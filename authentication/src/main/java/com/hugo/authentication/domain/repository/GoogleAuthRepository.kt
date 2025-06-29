package com.hugo.authentication.domain.repository

import android.content.Context
import com.hugo.authentication.domain.model.GoogleSignInResult

interface GoogleAuthRepository {
    suspend fun signInWithGoogle(context: Context): Result<GoogleSignInResult>
    suspend fun signOut(context: Context): Result<Unit>
}