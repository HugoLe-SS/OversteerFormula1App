package com.hugo.authentication.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.hugo.authentication.data.local.UserPreferences
import com.hugo.authentication.domain.model.GoogleSignInResult
import com.hugo.authentication.domain.repository.GoogleAuthRepository
import com.hugo.authentication.domain.repository.UserProfileRepository
import com.hugo.utilities.constants.AppConstants.WEB_CLIENT_ID
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleAuthRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences,
    private val supabaseAuth: Auth, // Supabase Auth client
    private val userProfileRepository: UserProfileRepository
): GoogleAuthRepository {

    override suspend fun signInWithGoogle(context: Context): Result<GoogleSignInResult> {
        Log.d("GoogleSignIn", "WEB_CLIENT_ID: $WEB_CLIENT_ID")
        return try {
            // First try with authorized accounts (returning users)
            val authorizedResult = attemptSignIn(context, filterByAuthorized = true)
            authorizedResult ?: run {
                Log.d("GoogleSignIn", "No authorized accounts found, showing account picker")
                // If no authorized accounts, try sign-up flow (new users)
                attemptSignIn(context, filterByAuthorized = false)
                    ?: Result.failure(Exception("No Google accounts available"))
            }
        } catch (e: Exception) {
            Log.e("GoogleSignIn", "Sign-in failed", e)
            Result.failure(e)
        }
    }

    override suspend fun signOut(context: Context): Result<Unit> {
        return try {
            userPreferences.clearUser()// clear user data from local storage
            supabaseAuth.signOut() // Sign out from Supabase Auth

            val credentialManager = CredentialManager.create(context)
            val request = ClearCredentialStateRequest()
            credentialManager.clearCredentialState(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isUserSignedIn(): Boolean {
        return userPreferences.isUserSignedIn()
    }

    override suspend fun getCachedUser(): GoogleSignInResult? {
        return userPreferences.getUserData()
    }

    override fun observeAuthState(): Flow<Boolean> {
        return userPreferences.isUserSignedInFlow
    }

    override fun observeUserData(): Flow<GoogleSignInResult?> {
        return userPreferences.userDataFlow
    }

    private suspend fun attemptSignIn(
        context: Context,
        filterByAuthorized: Boolean
    ): Result<GoogleSignInResult>? {
        val credentialManager = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val hashedNonce = hashNonce(rawNonce)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(filterByAuthorized)
            .setFilterByAuthorizedAccounts(filterByAuthorized)
            .setNonce(hashedNonce)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            val googleSignInResult = handleGoogleCredential(result, rawNonce)
            val finalResult = signInWithSupabaseAndSync(googleSignInResult)
            if (finalResult.isSuccess) {
                finalResult.getOrNull()?.let {
                    Log.d("GoogleSignIn", "Sign-in successful, saving user data")
                    userPreferences.saveUser(it)
                }
            }
            finalResult
//            handleCredentialResult(result).also { authResult ->
//                // Save user data on successful sign-in
//                if (authResult.isSuccess) {
//                    authResult.getOrNull()?.let {
//                        Log.d("GoogleSignIn", "Sign-in successful, saving user data")
//                        userPreferences.saveUser(it)
//                    }
//                }
//            }

        } catch (e: GetCredentialException) {
            when (e) {
                is NoCredentialException -> {
                    Log.d("GoogleSignIn", "No credentials found for filterByAuthorized: $filterByAuthorized")
                    null
                }
                else -> throw e
            }
        }
    }

    private fun handleGoogleCredential(result: GetCredentialResponse, rawNonce: String): GoogleSignInResult {
        val credential = result.credential
        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleId = GoogleIdTokenCredential.createFrom(credential.data)
                    return GoogleSignInResult(
                        idToken = googleId.idToken,
                        displayName = googleId.displayName,
                        email = googleId.id,
                        profilePictureUrl = googleId.profilePictureUri?.toString(),
                        userId = "", // Will be replaced by Supabase ID
                        nonce = rawNonce // IMPORTANT: Store the nonce
                    )
                } else {
                    throw Exception("Unsupported credential type: ${credential.type}")
                }
            }
            else -> throw Exception("Unexpected credential type: $credential")
        }
    }


    private suspend fun signInWithSupabaseAndSync(googleResult: GoogleSignInResult): Result<GoogleSignInResult> {
        return try {
            // Step 1: Perform the sign-in. This function returns Unit on success.
            supabaseAuth.signInWith(IDToken) {
                provider = Google
                idToken = googleResult.idToken
                nonce = googleResult.nonce // IMPORTANT SECURITY FIX: Pass the nonce
            }

            // Step 2: Retrieve the user object AFTER successful sign-in.
            val supabaseUser = supabaseAuth.currentUserOrNull()
                ?: throw Exception("Supabase sign-in failed: user is null after sign-in attempt")

            // Step 3: Sync the user profile using the correct user ID
            userProfileRepository.syncUserProfile(googleResult, supabaseUser.id).getOrThrow()

            // Step 4: Create the final result with the correct Supabase ID
            val finalResult = googleResult.copy(
                userId = supabaseUser.id,
                nonce = null // Clear the single-use nonce before saving
            )

            Result.success(finalResult)
        } catch (e: Exception) {
            Log.e("SupabaseSignIn", "Failed to sign in with Supabase or sync profile", e)
            Result.failure(e)
        }
    }


    private fun hashNonce(raw: String): String {
        val bytes = raw.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }


//    private fun handleCredentialResult(result: GetCredentialResponse): Result<GoogleSignInResult> {
//        val credential = result.credential
//
//        return when (credential) {
//            is CustomCredential -> {
//                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//                    try {
//                        val googleId = GoogleIdTokenCredential.createFrom(credential.data)
//                        val signInResult = GoogleSignInResult(
//                            idToken = googleId.idToken,
//                            displayName = googleId.displayName,
//                            email = googleId.id,
//                            profilePictureUrl = googleId.profilePictureUri?.toString(),
//                            userId = "temp_${googleId.id.hashCode()}"
//                        )
//                        Result.success(signInResult)
//                    } catch (e: GoogleIdTokenParsingException) {
//                        Result.failure(Exception("Invalid Google ID token", e))
//                    }
//                } else {
//                    Result.failure(Exception("Unsupported credential type: ${credential.type}"))
//                }
//            }
//            // Handle Password credentials (if support them)
//            is PasswordCredential -> {
//                // You could implement traditional password sign-in here if needed
//                Result.failure(Exception("Password authentication not implemented"))
//            }
//
//            // Handle Passkey credentials (if support them)
//            is PublicKeyCredential -> {
//                // You could implement passkey authentication here if needed
//                Result.failure(Exception("Passkey authentication not implemented"))
//            }
//            else -> Result.failure(Exception("Unexpected credential type"))
//        }
//    }



}