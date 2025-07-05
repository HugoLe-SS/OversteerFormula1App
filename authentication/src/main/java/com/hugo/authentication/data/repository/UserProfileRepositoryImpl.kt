package com.hugo.authentication.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.hugo.authentication.data.dto.ProfileDto
import com.hugo.authentication.data.local.UserPreferences
import com.hugo.authentication.data.mapper.toGoogleSignInResult
import com.hugo.authentication.domain.model.GoogleSignInResult
import com.hugo.authentication.domain.model.ProfileUpdate
import com.hugo.authentication.domain.repository.UserProfileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val supabaseAuth: Auth,
    private val supabaseStorage: Storage,
    @ApplicationContext private val context: Context,
    private val userPreferences: UserPreferences
): UserProfileRepository {

    override suspend fun syncUserProfile(
        user: GoogleSignInResult,
        supabaseUserId: String
    ): Result<Unit> {
        return try {
            val profileData = buildJsonObject {
                put("id", supabaseUserId) // Use the real Supabase Auth ID
                put("email", user.email)
                put("display_name", user.displayName)
                put("avatar_url", user.profilePictureUrl)
            }

            // Use upsert for a single, atomic operation.
            // It will INSERT if the 'id' doesn't exist, or UPDATE if it does.
            postgrest.from("Profiles").upsert(profileData)

            Result.success(Unit)
        } catch (e: Exception) {
            // Log the exception
            Log.e("UserProfileSync", "Failed to sync user profile", e)
            Result.failure(e)
        }
    }

    override suspend fun updateUserProfile(profileUpdate: ProfileUpdate): Result<GoogleSignInResult> {
        return try {
            val currentUser = supabaseAuth.currentUserOrNull()
                ?: return Result.failure(Exception("User not authenticated"))
            val userId = currentUser.id

            val profileUpdateData = buildJsonObject {
                put("display_name", profileUpdate.displayName)
                profileUpdate.avatarUrl?.let { put("avatar_url", it) }
            }

            // Update the remote database and get the fresh data back
            val updatedProfileDto = postgrest.from("Profiles")
                .update(profileUpdateData) {
                    filter {
                        eq("id", userId)
                    }
                    select()
                }
                .decodeSingle<ProfileDto>()

            val cachedUserData = userPreferences.getUserData()
            val idToken = cachedUserData?.idToken ?: ""
            val email = cachedUserData?.email ?: ""

            // Map DTO to domain model
            val finalResult = updatedProfileDto.toGoogleSignInResult(
                idToken = idToken,
                email = email
            )

            // Save the fresh data to the local cache
            userPreferences.saveUser(finalResult)

            Result.success(finalResult)
        } catch (e: Exception) {
            Log.e("ProfileUpdate", "Failed to update profile", e)
            Result.failure(e)
        }
    }

    override suspend fun uploadAvatar(uri: Uri): Result<String> {
        return try {
            val user = supabaseAuth.currentUserOrNull()
                ?: return Result.failure(Exception("User not authenticated"))

            // Generate a unique, stable file path for the user's avatar.
            // Using the user's ID is perfect for this. It also means
            // uploading a new avatar will automatically overwrite the old one.
            val filePath = "${user.id}/avatar.jpg"

            // Read the file's binary data from the Uri provided by the image picker
            val fileBytes = context.contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            } ?: return Result.failure(Exception("Could not read image from Uri"))

            // Upload the bytes to the "avatars" bucket in Supabase Storage.
            supabaseStorage.from("avatars").upload(
                path = filePath,
                data = fileBytes,
                {
                    upsert = true
                }
            )

            // After a successful upload, get the public URL for the file.
            val publicUrl = supabaseStorage.from("avatars").publicUrl(filePath)

            Result.success(publicUrl)
        } catch (e: Exception) {
            Log.e("AvatarUpload", "Failed to upload avatar", e)
            Result.failure(e)
        }
    }

}