package com.hugo.authentication.domain.repository

import com.hugo.authentication.domain.model.GoogleSignInResult

interface UserProfileRepository {

    suspend fun syncUserProfile(user: GoogleSignInResult, supabaseUserId: String): Result<Unit>
    suspend fun updateUserProfile(userId: String, displayName: String, bio: String)

}