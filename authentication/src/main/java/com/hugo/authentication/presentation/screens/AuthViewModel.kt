package com.hugo.authentication.presentation.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.authentication.domain.repository.GoogleAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null
            )

            googleAuthRepository.signInWithGoogle(context)
                .onSuccess { result ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSignedIn = true,
                        userInfo = result,
                        errorMessage = null
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSignedIn = false,
                        userInfo = null,
                        errorMessage = exception.message ?: "Sign-in failed"
                    )
                }
        }
    }

    fun signOut(context: Context) {
        viewModelScope.launch {
            googleAuthRepository.signOut(context)
                .onSuccess {
                    _state.value = AuthUiState() // Reset to initial state
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        errorMessage = exception.message ?: "Sign-out failed"
                    )
                }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }

}