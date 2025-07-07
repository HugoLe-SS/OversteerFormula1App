package com.hugo.authentication.presentation.screens.Auth

import android.app.Application
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.authentication.domain.model.ProfileUpdate
import com.hugo.authentication.domain.repository.GoogleAuthRepository
import com.hugo.authentication.domain.repository.UserProfileRepository
import com.hugo.utilities.logging.AppLogger
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
    private val userProfileRepository: UserProfileRepository,
    private val application: Application
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            googleAuthRepository.observeUserData().collect { userInfo ->
                _state.update {
                    it.copy(
                        isInitialLoading = false,
                        userInfo = userInfo,
                        isSignedIn = (userInfo != null),
                        editableDisplayName = userInfo?.displayName ?: ""
                    )
                }
            }
        }
    }

    fun signInWithGoogle() { // NO context parameter
        val context = application.applicationContext
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            googleAuthRepository.signInWithGoogle(context)
                .onSuccess {
                    _state.update { it.copy(
                        isLoading = false,
                    ) }
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(isLoading = false, errorMessage = e.message ?: "Sign-in failed")
                    }
                }
        }
    }

    fun signOut() { // NO context parameter
        val context = application.applicationContext
        viewModelScope.launch {
            googleAuthRepository.signOut(context)
                .onFailure { e ->
                    _state.update { it.copy(errorMessage = e.message ?: "Sign-out failed") }
                }
            // No onSuccess needed, the flow collector handles it
        }
    }

    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }

    // Called when the user types in the name TextField
    fun onDisplayNameChange(newName: String) {
        _state.update { it.copy(editableDisplayName = newName) }
    }

    // Called when the user selects a new avatar image
    fun onAvatarUriSelected(uri: Uri?) {
        uri ?: return // Ignore if user cancelled
        _state.update { it.copy(newAvatarUri = uri) }
    }

    // Called when the user clicks the "Save" button
    fun saveChanges() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, isUpdateSuccessful = false) }

            val currentState = _state.value
            var finalAvatarUrl = currentState.userInfo?.profilePictureUrl //Get the old URL

            // --- STEP 1: TALK TO STORAGE ---
            // If a new avatar was selected, upload it first
            if (currentState.newAvatarUri != null) {
                userProfileRepository.uploadAvatar(currentState.newAvatarUri)
                    .onSuccess { uploadedUrl ->
                        finalAvatarUrl = uploadedUrl
                    }
                    .onFailure { e ->
                        _state.update { it.copy(isLoading = false, errorMessage = e.message) }
                        return@launch // Stop if upload fails
                    }
            }

            // --- STEP 2: TALK TO THE DATABASE ---
            // Now, your app has the final URL (either the new one or the old one).
            // It creates the update object.
            val updateData = ProfileUpdate(
                displayName = currentState.editableDisplayName,
                avatarUrl = finalAvatarUrl
            )

            // Step 3: Update the profile in the repository
            userProfileRepository.updateUserProfile(updateData)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isUpdateSuccessful = true) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            userProfileRepository.deleteAccount()
                .onSuccess {
                    AppLogger.d(message = "Account successfully deleted on the server.")

                    googleAuthRepository.signOut(application.applicationContext)
                        .onSuccess {
                            AppLogger.d(message =  "Local sign-out successful, UI state will now reset.")
                        }
                        .onFailure { e ->
                            _state.update { it.copy(isLoading = false, errorMessage = "Account deleted, but local sign-out failed: ${e.message}") }
                        }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
        }
    }



}