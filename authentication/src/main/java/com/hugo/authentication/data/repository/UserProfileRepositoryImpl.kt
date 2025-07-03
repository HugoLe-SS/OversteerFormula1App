package com.hugo.authentication.data.repository

import android.util.Log
import com.hugo.authentication.domain.model.GoogleSignInResult
import com.hugo.authentication.domain.repository.UserProfileRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
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

    override suspend fun updateUserProfile(userId: String, displayName: String, bio: String) {
        TODO("Not yet implemented")
    }

}