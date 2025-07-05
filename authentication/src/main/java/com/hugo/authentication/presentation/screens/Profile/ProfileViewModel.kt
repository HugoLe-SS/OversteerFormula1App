package com.hugo.authentication.presentation.screens.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.authentication.domain.model.ProfileUpdate
import com.hugo.authentication.domain.repository.GoogleAuthRepository
import com.hugo.authentication.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val authRepository: GoogleAuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _state.asStateFlow()

    init {
        // When the screen opens, pre-fill the fields with the current user's data
        viewModelScope.launch {
            val currentUser = authRepository.getCachedUser()
            _state.update {
                it.copy(
                    displayName = currentUser?.displayName ?: "",
                    avatarUrl = currentUser?.profilePictureUrl ?: "",
                )
            }
        }
    }

    // This function is called by the UI whenever the text in the TextField changes
    fun onDisplayNameChange(newName: String) {
        _state.update { it.copy(displayName = newName) }
    }

    // This is the function the "Save" button will call
    fun onSaveChangesClicked() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val updateData = ProfileUpdate(
                displayName = _state.value.displayName,
                avatarUrl = null // We'll handle avatar later
            )

            // Call the repository function
            userProfileRepository.updateUserProfile(updateData)
                .onSuccess {
                    _state.update {
                        it.copy(isLoading = false, isUpdateSuccessful = true)
                    }
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(isLoading = false, errorMessage = e.message)
                    }
                }
        }
    }
}
