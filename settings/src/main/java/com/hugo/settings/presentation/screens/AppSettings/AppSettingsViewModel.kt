package com.hugo.settings.presentation.screens.AppSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.datasource.local.UserPreferences
import com.hugo.datasource.local.entity.User.ThemePreference
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel@Inject constructor(
    private val userPreferences: UserPreferences
):ViewModel() {
    private val _state = MutableStateFlow(AppSettingsUiState())
    val state: StateFlow<AppSettingsUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferences.themePreferenceFlow.collect { theme ->
                _state.update { it.copy(currentTheme = theme) }
            }
        }
    }

    fun onThemeChanged(theme: ThemePreference) {
        viewModelScope.launch {
            userPreferences.updateThemePreference(theme)
            AppLogger.d( message = "Theme changed to: ${theme.name}")
        }
    }
}