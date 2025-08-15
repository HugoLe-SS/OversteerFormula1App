package com.hugo.settings.presentation.screens.AppSettings

import com.hugo.datasource.local.entity.User.ThemePreference

data class AppSettingsUiState(
    val currentTheme: ThemePreference = ThemePreference.SYSTEM
)
