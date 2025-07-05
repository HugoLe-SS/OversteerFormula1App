package com.hugo.account.presentation.screens

import com.hugo.authentication.domain.model.GoogleSignInResult

data class SettingsUIState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userInfo: GoogleSignInResult? = null

)