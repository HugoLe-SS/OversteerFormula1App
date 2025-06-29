package com.hugo.authentication.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hugo.design.ui.theme.AppTheme

//@Composable
//fun GoogleSignInButton(
//    onSignInSuccess: (GoogleSignInResult) -> Unit = {},
//    onSignInFailure: (String) -> Unit = {},
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//    var isLoading by remember { mutableStateOf(false) }
//
//    // Generate nonce for security
//    val rawNonce = remember { UUID.randomUUID().toString() }
//    val hashedNonce = remember {
//        val bytes = rawNonce.toByteArray()
//        val md = MessageDigest.getInstance("SHA-256")
//        val digest = md.digest(bytes)
//        digest.fold("") { str, it -> str + "%02x".format(it) }
//    }
//
//    fun performSignIn(filterByAuthorized: Boolean) {
//        if (!isLoading) {
//            isLoading = true
//            val credentialManager = CredentialManager.create(context)
//
//            val googleIdOption = GetGoogleIdOption.Builder()
//                .setFilterByAuthorizedAccounts(filterByAuthorized)
//                .setServerClientId(WEB_CLIENT_ID)
//                .setAutoSelectEnabled(true)
//                .setNonce(hashedNonce)
//                .build()
//
//            val request = GetCredentialRequest.Builder()
//                .addCredentialOption(googleIdOption)
//                .build()
//
//            coroutineScope.launch {
//                try {
//                    val result = credentialManager.getCredential(
//                        request = request,
//                        context = context,
//                    )
//                    handleSignIn(result, rawNonce, onSignInSuccess, onSignInFailure)
//                } catch (e: GetCredentialException) {
//                    when (e) {
//                        is NoCredentialException -> {
//                            if (filterByAuthorized) {
//                                // No authorized accounts found, try with all accounts for sign-up
//                                Log.d("GoogleSignIn", "No authorized accounts, trying sign-up flow")
//                                performSignIn(false)
//                                return@launch // Don't set isLoading to false yet
//                            } else {
//                                Log.e("GoogleSignIn", "No Google accounts available", e)
//                                onSignInFailure("No Google accounts found on device")
//                            }
//                        }
//                        else -> {
//                            Log.e("GoogleSignIn", "Sign-in failed", e)
//                            onSignInFailure(e.message ?: "Sign-in failed")
//                        }
//                    }
//                } finally {
//                    isLoading = false
//                }
//            }
//        }
//    }
//
//    val signInClick = {
//        // Start with authorized accounts first (recommended flow)
//        performSignIn(true)
//    }
//
//    Column(
//        modifier = modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(
//            onClick = signInClick,
//            enabled = !isLoading,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 32.dp)
//                .height(48.dp)
//        ) {
//            if (isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier.size(20.dp),
//                    color = AppTheme.colorScheme.onPrimary
//                )
//            } else {
//                Text("Sign in with Google")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "By signing up, you agree to our Terms of Service",
//            style = AppTheme.typography.labelMini,
//            color = AppTheme.colorScheme.onSecondary,
//        )
//    }
//}
//
//// Data class to hold sign-in result
//data class GoogleSignInResult(
//    val idToken: String,
//    val displayName: String?,
//    val email: String?,
//    val profilePictureUrl: String?,
//    val userId: String
//)
//
//fun handleSignIn(
//    result: GetCredentialResponse,
//    originalNonce: String,
//    onSuccess: (GoogleSignInResult) -> Unit,
//    onFailure: (String) -> Unit
//) {
//    val credential = result.credential
//    val TAG = "GoogleSignIn"
//
//    when (credential) {
//        // Handle Passkey credential
//        is PublicKeyCredential -> {
//            Log.d(TAG, "Received PublicKeyCredential")
//            // Handle passkey authentication if needed
//            // val responseJson = credential.authenticationResponseJson
//            onFailure("Passkey authentication not implemented")
//        }
//
//        // Handle Password credential
//        is PasswordCredential -> {
//            Log.d(TAG, "Received PasswordCredential")
//            // Handle traditional password sign-in if needed
//            // val username = credential.id
//            // val password = credential.password
//            onFailure("Password authentication not implemented")
//        }
//
//        // Handle Google ID Token credential
//        is CustomCredential -> {
//            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//                try {
//                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
//
//                    // Extract user information from the credential
//                    val idToken = googleIdTokenCredential.idToken
//                    val displayName = googleIdTokenCredential.displayName
//                    val email = googleIdTokenCredential.id // This is the email
//                    val profilePictureUrl = googleIdTokenCredential.profilePictureUri?.toString()
//
//                    // Validate nonce if needed (recommended for security)
//                    // You should also validate the token on your backend server
//                    // and extract the subject ID for stable user identification
//
//                    // For now, create a temporary user ID
//                    // In production, validate the token on your backend and use the subject ID
//                    val userId = "temp_${email?.hashCode()}"
//
//                    Log.d(TAG, "Sign-in successful for user: $email")
//                    Log.d(TAG, "Display name: $displayName")
//
//                    // TODO: Send idToken to your backend for validation
//                    // Your backend should:
//                    // 1. Verify the token signature
//                    // 2. Verify the nonce matches
//                    // 3. Extract the subject ID for stable user identification
//                    // 4. Return your app's user data
//
//                    val signInResult = GoogleSignInResult(
//                        idToken = idToken,
//                        displayName = displayName,
//                        email = email,
//                        profilePictureUrl = profilePictureUrl,
//                        userId = userId
//                    )
//
//                    onSuccess(signInResult)
//
//                } catch (e: GoogleIdTokenParsingException) {
//                    Log.e(TAG, "Received an invalid google id token response", e)
//                    onFailure("Invalid Google ID token received")
//                }
//            } else {
//                Log.e(TAG, "Unexpected type of credential: ${credential.type}")
//                onFailure("Unexpected credential type received")
//            }
//        }
//
//        else -> {
//            Log.e(TAG, "Unexpected type of credential")
//            onFailure("Unexpected credential type")
//        }
//    }
//}

// Function to handle sign-out (call this when user signs out)
//suspend fun signOut(context: android.content.Context) {
//    try {
//        val credentialManager = CredentialManager.create(context)
//        credentialManager.clearCredentialState()
//        Log.d("GoogleSignIn", "Credential state cleared")
//    } catch (e: Exception) {
//        Log.e("GoogleSignIn", "Error clearing credential state", e)
//    }
//}

// Usage example in your main composable
//@Composable
//fun SignInScreen() {
//    var signInResult by remember { mutableStateOf<GoogleSignInResult?>(null) }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//
//    if (signInResult != null) {
//        // User is signed in - show success UI
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "Welcome, ${signInResult!!.displayName ?: "User"}!",
//                style = MaterialTheme.typography.headlineMedium
//            )
//            Text(
//                text = "Email: ${signInResult!!.email}",
//                style = MaterialTheme.typography.bodyMedium
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                    coroutineScope.launch {
//                        signOut(context)
//                        signInResult = null
//                        errorMessage = null
//                    }
//                }
//            ) {
//                Text("Sign Out")
//            }
//        }
//    } else {
//        // Show sign-in UI
//        GoogleSignInButton(
//            onSignInSuccess = { result ->
//                signInResult = result
//                errorMessage = null
//            },
//            onSignInFailure = { error ->
//                errorMessage = error
//                Log.e("SignInScreen", "Sign-in failed: $error")
//            }
//        )
//
//        // Show error message if any
//        errorMessage?.let { error ->
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Error: $error",
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//    }
//}

@Preview
@Composable
fun GoogleSignInButtonPreview() {
    AppTheme(isDarkTheme = true) {
        //GoogleSignInButton()
    }
}