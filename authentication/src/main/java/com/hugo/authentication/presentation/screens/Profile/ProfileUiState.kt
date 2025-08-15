package com.hugo.authentication.presentation.screens.Profile

data class ProfileUiState (
    val isLoading: Boolean = false,
    val displayName: String = "",
    val avatarUrl: String = "",
    val errorMessage: String? = null,
    val isUpdateSuccessful: Boolean = false
)