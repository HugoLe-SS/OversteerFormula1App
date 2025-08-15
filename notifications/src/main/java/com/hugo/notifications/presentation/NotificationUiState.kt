package com.hugo.notifications.presentation

import com.hugo.datasource.local.entity.User.NotificationSettings

data class NotificationUiState(
    val isLoading: Boolean = false,
    val settings: NotificationSettings = NotificationSettings(true, true, true, true),
    val errorMessage: String? = null
)
