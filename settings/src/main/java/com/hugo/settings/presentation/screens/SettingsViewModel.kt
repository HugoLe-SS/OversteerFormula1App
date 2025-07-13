package com.hugo.settings.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.authentication.domain.repository.GoogleAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: GoogleAuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(SettingsUIState())
    val state: StateFlow<SettingsUIState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.observeUserData().collect { user ->
                _state.update { it.copy(userInfo = user) }
            }
        }
    }
}