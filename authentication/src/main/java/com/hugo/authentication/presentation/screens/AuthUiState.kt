package com.hugo.authentication.presentation.screens

import com.hugo.authentication.domain.model.GoogleSignInResult

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val userInfo: GoogleSignInResult? = null,
    val errorMessage: String? = null
)
