package com.hugo.authentication.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.hugo.authentication.domain.model.GoogleSignInResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")
    private val dataStore = context.dataStore

    suspend fun saveUser(signInResult: GoogleSignInResult) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.ID_TOKEN] = signInResult.idToken
            prefs[PreferenceKeys.USER_ID] = signInResult.userId
            prefs[PreferenceKeys.EMAIL] = signInResult.email?: ""
            prefs[PreferenceKeys.DISPLAY_NAME] = signInResult.displayName ?: ""
            prefs[PreferenceKeys.PROFILE_PICTURE_URL] = signInResult.profilePictureUrl ?: ""
        }
    }

    // Flow to observe login state
    val isUserSignedInFlow: Flow<Boolean> = dataStore.data.map { prefs ->
        !prefs[PreferenceKeys.ID_TOKEN].isNullOrEmpty()
    }

    // Flow to get user data
    val userDataFlow: Flow<GoogleSignInResult?> = dataStore.data.map { prefs ->
        val idToken = prefs[PreferenceKeys.ID_TOKEN]
        val userId = prefs[PreferenceKeys.USER_ID]
        val email = prefs[PreferenceKeys.EMAIL]

        if (idToken != null && userId != null && email != null) {
            GoogleSignInResult(
                idToken = idToken,
                userId = userId,
                email = email,
                displayName = prefs[PreferenceKeys.DISPLAY_NAME],
                profilePictureUrl = prefs[PreferenceKeys.PROFILE_PICTURE_URL]
            )
        } else {
            null
        }
    }

    // Suspend function to get user data once
    suspend fun getUserData(): GoogleSignInResult? {
        return userDataFlow.first()
    }

    // Suspend function to check if user is signed in
    suspend fun isUserSignedIn(): Boolean {
        return isUserSignedInFlow.first()
    }

    suspend fun clearUser() {
        dataStore.edit { it.clear() }
    }
}