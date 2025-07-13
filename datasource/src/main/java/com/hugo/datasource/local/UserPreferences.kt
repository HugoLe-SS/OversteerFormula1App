package com.hugo.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.hugo.datasource.local.entity.User.GoogleSignInResult
import com.hugo.datasource.local.entity.User.NotificationSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")
    private val dataStore = context.dataStore


    // Flow to observe login state
    val isUserSignedInFlow: Flow<Boolean> = dataStore.data.map { prefs ->
        !prefs[com.hugo.datasource.local.PreferenceKeys.ID_TOKEN].isNullOrEmpty()
    }

    // Flow to get user data
    val userDataFlow: Flow<GoogleSignInResult?> = dataStore.data.map { prefs ->
        val idToken = prefs[com.hugo.datasource.local.PreferenceKeys.ID_TOKEN]
        val userId = prefs[com.hugo.datasource.local.PreferenceKeys.USER_ID]
        val email = prefs[com.hugo.datasource.local.PreferenceKeys.EMAIL]

        if (idToken != null && userId != null && email != null) {
            GoogleSignInResult(
                idToken = idToken,
                userId = userId,
                email = email,
                displayName = prefs[com.hugo.datasource.local.PreferenceKeys.DISPLAY_NAME],
                profilePictureUrl = prefs[com.hugo.datasource.local.PreferenceKeys.PROFILE_PICTURE_URL]
            )
        } else {
            null
        }
    }

    // Flow to observe all notification settings at once
    val notificationSettingsFlow: Flow<NotificationSettings> = dataStore.data.map { prefs ->
        NotificationSettings(
            raceStarts = prefs[PreferenceKeys.NOTIF_RACE_STARTS] ?: true,
            allSessions = prefs[PreferenceKeys.NOTIF_ALL_SESSIONS] ?: true,
            breakingNews = prefs[PreferenceKeys.NOTIF_BREAKING_NEWS] ?: true,
            general = prefs[PreferenceKeys.NOTIF_GENERAL] ?: true
        )
    }

    suspend fun updateBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun saveFcmToken(token: String) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.FCM_TOKEN] = token
        }
    }

    suspend fun getFcmToken(): String? {
        return dataStore.data.first()[PreferenceKeys.FCM_TOKEN]
    }

    // save user data
    suspend fun saveUser(signInResult: GoogleSignInResult) {
        dataStore.edit { prefs ->
            prefs[com.hugo.datasource.local.PreferenceKeys.ID_TOKEN] = signInResult.idToken
            prefs[com.hugo.datasource.local.PreferenceKeys.USER_ID] = signInResult.userId
            prefs[com.hugo.datasource.local.PreferenceKeys.EMAIL] = signInResult.email?: ""
            prefs[com.hugo.datasource.local.PreferenceKeys.DISPLAY_NAME] = signInResult.displayName ?: ""
            prefs[com.hugo.datasource.local.PreferenceKeys.PROFILE_PICTURE_URL] = signInResult.profilePictureUrl ?: ""
        }
    }

    //  get user data once
    suspend fun getUserData(): GoogleSignInResult? {
        return userDataFlow.first()
    }

    //  check if user is signed in
    suspend fun isUserSignedIn(): Boolean {
        return isUserSignedInFlow.first()
    }

    suspend fun clearUser() {
        dataStore.edit { it.clear() }
    }


}