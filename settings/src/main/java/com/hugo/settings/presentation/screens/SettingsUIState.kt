package com.hugo.settings.presentation.screens

import com.hugo.datasource.local.entity.User.GoogleSignInResult

data class SettingsUIState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userInfo: GoogleSignInResult? = null

)