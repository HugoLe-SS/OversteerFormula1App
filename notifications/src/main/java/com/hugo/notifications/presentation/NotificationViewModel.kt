package com.hugo.notifications.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.datasource.local.UserPreferences
import com.hugo.notifications.domain.NotificationRepository
import com.hugo.notifications.domain.NotificationSettingType
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val notificationRepository: NotificationRepository
): ViewModel() {

    private val _state = MutableStateFlow(NotificationUiState())
    val state: StateFlow<NotificationUiState> = _state.asStateFlow()

    init {
        // When the ViewModel starts, it listens to the "saved" state from UserPreferences
        // and uses it to populate the initial UI state.
        viewModelScope.launch {
            userPreferences.notificationSettingsFlow.collect { savedSettings ->
                _state.update { it.copy(settings = savedSettings) }
            }
        }
    }

    // This is the new "Optimistic UI" function
    fun onSettingChanged(setting: NotificationSettingType, isEnabled: Boolean) {

        // --- STEP 1: IMMEDIATELY UPDATE THE LOCAL UI STATE ---
        // We get the current settings, create a modified copy, and update the state flow.
        // The UI will recompose instantly with this new temporary state.
        val currentSettings = _state.value.settings
        val newOptimisticSettings = when (setting) {
            NotificationSettingType.RACE_STARTS -> currentSettings.copy(raceStarts = isEnabled)
            NotificationSettingType.ALL_SESSIONS -> currentSettings.copy(allSessions = isEnabled)
            NotificationSettingType.BREAKING_NEWS -> currentSettings.copy(breakingNews = isEnabled)
            NotificationSettingType.GENERAL -> currentSettings.copy(general = isEnabled)
        }
        _state.update { it.copy(settings = newOptimisticSettings) }
        AppLogger.d(message =  "Optimistically updated UI for $setting to $isEnabled")


        // --- STEP 2: TRY TO SAVE THE CHANGE IN THE BACKGROUND ---
        viewModelScope.launch {
            val result = notificationRepository.updateNotificationSetting(setting, isEnabled)

            if (result.isFailure) {
                // The save failed! We don't need to manually revert the UI state.
                // The `userPreferences.notificationSettingsFlow` in our `init` block
                // is still emitting the OLD, saved value. The next time the user
                // re-enters the screen, it will show the correct, saved state.
                // We just need to show an error message.
                AppLogger.e(message =  "Remote update failed for $setting. UI will revert on next load.")
                _state.update { it.copy(errorMessage = "Couldn't save setting. Please check your connection.") }
            } else {
                // Success! The server and local cache are now updated.
                // The `notificationSettingsFlow` will soon emit the new saved state,
                // which will match our optimistic UI state. Everything is in sync.
                AppLogger.d(message =  "Remote update successful for $setting.")
            }
        }
    }

//    val settingsState: StateFlow<NotificationSettings> =
//        userPreferences.notificationSettingsFlow
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000), // Standard configuration
//                initialValue = NotificationSettings(true, true, true, true)
//            )
//
//    fun onSettingChanged(setting: NotificationSettingType, isEnabled: Boolean) {
//        viewModelScope.launch {
//            // STEP 1: Immediately update the local state.
//            // The UI will instantly change because it's observing the flow from UserPreferences.
//            val key = getLocalKey(setting) // A helper function to get the correct key
//            userPreferences.updateBoolean(key, isEnabled)
//            AppLogger.d(message = "Optimistically updated UI for $setting to $isEnabled")
//
//            // STEP 2: Try to sync the change with the remote server.
//            val result = notificationRepository.updateNotificationSetting(setting, isEnabled)
//
//            // STEP 3: Handle the result.
//            if (result.isFailure) {
//                // The server update failed! We must roll back the local change.
//                AppLogger.e(message =  "Remote update failed for $setting. Rolling back UI.")
//
//                // Revert the preference to its previous state.
//                userPreferences.updateBoolean(key, !isEnabled)
//
//            } else {
//                // Success! The server state now matches the UI state. Do nothing.
//                AppLogger.d(message =  "Remote update successful for $setting.")
//            }
//        }
//    }

}