package com.hugo.authentication.domain.repository

import android.app.Activity
import com.hugo.datasource.local.entity.User.GoogleSignInResult
import kotlinx.coroutines.flow.Flow

interface GoogleAuthRepository {
    suspend fun signInWithGoogle(activity: Activity): Result<GoogleSignInResult>
    suspend fun signOut(): Result<Unit>
    suspend fun isUserSignedIn(): Boolean
    suspend fun getCachedUser(): GoogleSignInResult?

    fun observeAuthState(): Flow<Boolean>
    fun observeUserData(): Flow<GoogleSignInResult?>
}