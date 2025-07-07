package com.hugo.authentication.presentation.screens.Auth

import android.net.Uri
import com.hugo.authentication.domain.model.GoogleSignInResult

data class AuthUiState(
    val isInitialLoading: Boolean = true,

    // Existing states for auth flow
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val userInfo: GoogleSignInResult? = null,
    val errorMessage: String? = null,

    // --- NEW: States for editing profile ---
    // This holds the text currently in the TextField, separate from the saved userInfo
    val editableDisplayName: String = "",

    // This holds the URI of a newly selected avatar for preview
    val newAvatarUri: Uri? = null,

    // A flag to signal when the update is complete, to trigger navigation or a toast
    val isUpdateSuccessful: Boolean = false,
)

enum class AuthStatus {
    UNKNOWN, // The initial state, before we've checked local storage
    AUTHENTICATED,
    UNAUTHENTICATED
}
