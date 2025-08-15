package com.hugo.authentication.domain.repository

import android.net.Uri
import com.hugo.authentication.data.dto.ProfileDto
import com.hugo.authentication.domain.model.ProfileUpdate
import com.hugo.datasource.local.entity.User.GoogleSignInResult

interface UserProfileRepository {

    suspend fun getOrCreateProfile(googleResult: GoogleSignInResult, userId: String): Result<ProfileDto>
    suspend fun syncUserProfile(user: GoogleSignInResult, supabaseUserId: String): Result<Unit>
    suspend fun updateUserProfile(profileUpdate: ProfileUpdate): Result<GoogleSignInResult>
    suspend fun uploadAvatar(uri: Uri): Result<String> // Returns the public URL
    suspend fun deleteAccount(): Result<Unit>

}