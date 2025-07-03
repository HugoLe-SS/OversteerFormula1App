package com.hugo.account.presentation.screens

data class ProfileUIState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "",
    val userEmail: String = "",
    val userProfilePictureUrl: String? = null
)