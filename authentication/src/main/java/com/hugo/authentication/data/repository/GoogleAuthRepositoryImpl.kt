package com.hugo.authentication.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.hugo.authentication.domain.model.GoogleSignInResult
import com.hugo.authentication.domain.repository.GoogleAuthRepository
import com.hugo.utilities.constants.AppConstants.WEB_CLIENT_ID
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleAuthRepositoryImpl @Inject constructor(): GoogleAuthRepository {

    override suspend fun signInWithGoogle(context: Context): Result<GoogleSignInResult> {
        Log.d("GoogleSignIn", "WEB_CLIENT_ID: $WEB_CLIENT_ID")
        Log.d("GoogleSignIn", "Package name: ${context.packageName}")
        return try {
            // First try with authorized accounts (returning users)
            val authorizedResult = attemptSignIn(context, filterByAuthorized = true)
            authorizedResult ?: run {
                // If no authorized accounts, try sign-up flow (new users)
                attemptSignIn(context, filterByAuthorized = false)
                    ?: Result.failure(Exception("No Google accounts available"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut(context: Context): Result<Unit> {
        return try {
            val credentialManager = CredentialManager.create(context)
            val request = ClearCredentialStateRequest()
            credentialManager.clearCredentialState(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun attemptSignIn(
        context: Context,
        filterByAuthorized: Boolean
    ): Result<GoogleSignInResult>? {
        val credentialManager = CredentialManager.create(context)

        // Generate nonce for security
        val rawNonce = UUID.randomUUID().toString()
        val hashedNonce = hashNonce(rawNonce)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(true)
            .setFilterByAuthorizedAccounts(filterByAuthorized)
            .setNonce(hashedNonce)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            handleCredentialResult(result)
        } catch (e: GetCredentialException) {
            when (e) {
                is NoCredentialException -> {
                    // No credentials found for this filter type
                    null
                }
                else -> throw e
            }
        }
    }

    private fun handleCredentialResult(result: GetCredentialResponse): Result<GoogleSignInResult> {
        val credential = result.credential

        return when (credential) {
            // Handle Google ID Token
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleId = GoogleIdTokenCredential.createFrom(credential.data)
                        val signInResult = GoogleSignInResult(
                            idToken = googleId.idToken,
                            displayName = googleId.displayName,
                            email = googleId.id,
                            profilePictureUrl = googleId.profilePictureUri?.toString(),
                            userId = "temp_${googleId.id.hashCode()}" // TODO: Validate on backend
                        )
                        Result.success(signInResult)
                    } catch (e: GoogleIdTokenParsingException) {
                        Result.failure(Exception("Invalid Google ID token", e))
                    }
                } else {
                    Result.failure(Exception("Unsupported credential type: ${credential.type}"))
                }
            }

            // Handle Password credentials (if support them)
            is PasswordCredential -> {
                // You could implement traditional password sign-in here if needed
                Result.failure(Exception("Password authentication not implemented"))
            }

            // Handle Passkey credentials (if support them)
            is PublicKeyCredential -> {
                // You could implement passkey authentication here if needed
                Result.failure(Exception("Passkey authentication not implemented"))
            }

            else -> {
                Result.failure(Exception("Unexpected credential type"))
            }
        }
    }

    private fun hashNonce(raw: String): String {
        val bytes = raw.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}