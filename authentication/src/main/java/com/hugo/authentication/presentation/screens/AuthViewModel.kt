package com.hugo.authentication.presentation.screens

import android.content.Context
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
class AuthViewModel @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository,
) : ViewModel() {

        private val _state = MutableStateFlow(AuthUiState())
        val state: StateFlow<AuthUiState> = _state.asStateFlow()

        init {
            // Collect auth state and user data, update UI state accordingly
            viewModelScope.launch {
                googleAuthRepository.observeAuthState().collect { isSignedIn ->
                    _state.update { it.copy(isSignedIn = isSignedIn) }
                }
            }

            viewModelScope.launch {
                googleAuthRepository.observeUserData().collect { userInfo ->
                    _state.update { it.copy(userInfo = userInfo) }
                }
            }
        }

        fun signInWithGoogle(context: Context) {
            viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true, errorMessage = null)

                googleAuthRepository.signInWithGoogle(context)
                    .onSuccess {
                        _state.value = _state.value.copy(isLoading = false, errorMessage = null)
                    }
                    .onFailure { e ->
                        _state.value = _state.value.copy(isLoading = false, errorMessage = e.message ?: "Sign-in failed")
                    }
            }
        }

        fun signOut(context: Context) {
            viewModelScope.launch {
                googleAuthRepository.signOut(context)
                    .onSuccess {
                        _state.value = AuthUiState() // reset UI state
                    }
                    .onFailure { e ->
                        _state.value = _state.value.copy(errorMessage = e.message ?: "Sign-out failed")
                    }
            }
        }

        fun clearError() {
            _state.value = _state.value.copy(errorMessage = null)
        }


}