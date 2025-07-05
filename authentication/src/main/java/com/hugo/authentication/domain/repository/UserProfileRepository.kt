package com.hugo.authentication.domain.repository

import android.net.Uri
import com.hugo.authentication.domain.model.GoogleSignInResult
import com.hugo.authentication.domain.model.ProfileUpdate

interface UserProfileRepository {

    suspend fun syncUserProfile(user: GoogleSignInResult, supabaseUserId: String): Result<Unit>
    suspend fun updateUserProfile(profileUpdate: ProfileUpdate): Result<GoogleSignInResult>
    suspend fun uploadAvatar(uri: Uri): Result<String> // Returns the public URL

}