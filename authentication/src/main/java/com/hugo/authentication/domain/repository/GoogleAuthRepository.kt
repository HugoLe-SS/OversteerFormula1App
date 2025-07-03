package com.hugo.authentication.domain.repository

import android.content.Context
import com.hugo.authentication.domain.model.GoogleSignInResult
import kotlinx.coroutines.flow.Flow

interface GoogleAuthRepository {
    suspend fun signInWithGoogle(context: Context): Result<GoogleSignInResult>
    suspend fun signOut(context: Context): Result<Unit>
    suspend fun isUserSignedIn(): Boolean
    suspend fun getCachedUser(): GoogleSignInResult?

    fun observeAuthState(): Flow<Boolean>
    fun observeUserData(): Flow<GoogleSignInResult?>
}