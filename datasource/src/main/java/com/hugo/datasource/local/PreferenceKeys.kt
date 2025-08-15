package com.hugo.datasource.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val ID_TOKEN = stringPreferencesKey("id_token")
    val USER_ID = stringPreferencesKey("user_id")
    val EMAIL = stringPreferencesKey("email")
    val DISPLAY_NAME = stringPreferencesKey("display_name")
    val PROFILE_PICTURE_URL = stringPreferencesKey("profile_picture_url")

    // FCM Token Key
    val FCM_TOKEN = stringPreferencesKey("fcm_token")

    // Notification Preference Keys
    val NOTIF_RACE_STARTS = booleanPreferencesKey("notif_race_starts")
    val NOTIF_ALL_SESSIONS = booleanPreferencesKey("notif_all_sessions")
    val NOTIF_BREAKING_NEWS = booleanPreferencesKey("notif_breaking_news")
    val NOTIF_GENERAL = booleanPreferencesKey("notif_general")

    //App Theme
    val THEME_PREFERENCE = stringPreferencesKey("theme_preference")

}