package com.hugo.authentication.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.hugo.authentication.data.mapper.toGoogleSignInResult
import com.hugo.authentication.domain.repository.GoogleAuthRepository
import com.hugo.authentication.domain.repository.UserProfileRepository
import com.hugo.datasource.local.UserPreferences
import com.hugo.datasource.local.entity.User.GoogleSignInResult
import com.hugo.notifications.domain.NotificationRepository
import com.hugo.utilities.constants.AppConstants.WEB_CLIENT_ID
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
    private val userPreferences: UserPreferences,
    private val supabaseAuth: Auth, // Supabase Auth client
    private val userProfileRepository: UserProfileRepository,
    private val notificationRepository: NotificationRepository
): GoogleAuthRepository {


    private val credentialManager = CredentialManager.create(context)

    override suspend fun signInWithGoogle(): Result<GoogleSignInResult> {
        return try {
            Log.d("GoogleSignIn", "Attempting sign-in for authorized accounts...")
            val signInResult = attemptSignUp()
            Log.d("GoogleSignIn", "Sign-in successful for returning user.")

            return signInResult
        } catch (e: Exception) {
            Log.e("GoogleSignIn", "A critical error occurred during the sign-in/sign-up process", e)
            Result.failure(e)
        }
    }

    //1 tap sign in
    private suspend fun attemptSignIn(): Result<GoogleSignInResult> {
        // Generate nonce and build the option here
        val rawNonce = UUID.randomUUID().toString()
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(true)
            .setNonce(hashNonce(rawNonce)) // Set nonce at build time
            .build()

        // Call the shared logic
        return performGoogleAuth(googleIdOption, rawNonce)
    }

    // Sign in + sign up flow
    private suspend fun attemptSignUp(): Result<GoogleSignInResult> {
        val rawNonce = UUID.randomUUID().toString()

        // Use GetSignInWithGoogleOption to force the account chooser UI
        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID)
            .setNonce(hashNonce(rawNonce))
            // You can add other options here if needed, but this is the basic setup
            .build()

        // We now have a different type of option, so we need a slightly different request.
        // The shared 'performGoogleAuth' needs to be adjusted or duplicated.
        // Let's create a separate path for simplicity.

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption) // Use the new option
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            val googleSignInResult = handleGoogleCredential(result, rawNonce)
            val finalResult = signInWithSupabaseAndSync(googleSignInResult)

            if (finalResult.isSuccess) {
                finalResult.getOrNull()?.let { userPreferences.saveUser(it) }
            }
            finalResult
        } catch (e: GetCredentialException) {
            when (e) {
                is NoCredentialException, is GetCredentialCancellationException -> {
                    Log.w("GoogleSignIn", "Sign-up flow was cancelled by the user.", e)
                    Result.failure(Exception("Sign-up was cancelled by the user."))
                }
                else -> {
                    Log.e("GoogleSignIn", "A critical error occurred during sign-up.", e)
                    Result.failure(e) // Let the top-level handler catch it
                }
            }
        }
    }

    private suspend fun performGoogleAuth(
        googleIdOption: GetGoogleIdOption,
        rawNonce: String // Pass the raw nonce in
    ): Result<GoogleSignInResult> {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)

            // Pass rawNonce to the handler, which then passes it to Supabase
            val googleSignInResult = handleGoogleCredential(result, rawNonce)
            val finalResult = signInWithSupabaseAndSync(googleSignInResult)

            if (finalResult.isSuccess) {
                finalResult.getOrNull()?.let { userPreferences.saveUser(it) }
            }
            finalResult
        } catch (e: GetCredentialException) {
            // Only handle the expected exceptions here. Critical ones will be
            // caught by the top-level try/catch in signInWithGoogle().
            when (e) {
                is NoCredentialException, is GetCredentialCancellationException -> {
                    Log.w("GoogleSignIn", "Flow cancelled or no credentials for this option.", e)
                    Result.failure(e)
                }
                else -> throw e
            }
        }
    }

    override suspend fun signOut(): Result<Unit> {
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

    private fun handleGoogleCredential(result: GetCredentialResponse, rawNonce: String): GoogleSignInResult {
        val credential = result.credential
        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleId = GoogleIdTokenCredential.createFrom(credential.data)
                    return com.hugo.datasource.local.entity.User.GoogleSignInResult(
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
            // Step 1 & 2: Authenticate and get user (Your code is correct)
            supabaseAuth.signInWith(IDToken) {
                provider = Google
                idToken = googleResult.idToken
                nonce = googleResult.nonce
            }
            val supabaseUser = supabaseAuth.currentUserOrNull()
                ?: throw Exception("Supabase sign-in failed: user is null")

            // Step 3: Let the UserProfileRepository handle all profile logic.
            val finalProfileDto = userProfileRepository.getOrCreateProfile(googleResult, supabaseUser.id).getOrThrow()

            // Step 4: Map and save to cache
            val finalResult = finalProfileDto.toGoogleSignInResult(
                idToken = supabaseAuth.currentSessionOrNull()?.accessToken ?: "",
                email = supabaseUser.email?: ""
            )
            userPreferences.saveUser(finalResult)

            // Step 5: Sync FCM token
            notificationRepository.syncFcmToken()

            Result.success(finalResult)
        } catch (e: Exception) {
            Log.e("SupabaseSignIn", "Failed to sign in or sync profile", e)
            Result.failure(e)
        }
    }

    private fun hashNonce(raw: String): String {
        val bytes = raw.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }



}