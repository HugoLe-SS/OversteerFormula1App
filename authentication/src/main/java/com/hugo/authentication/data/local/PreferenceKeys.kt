package com.hugo.authentication.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val ID_TOKEN = stringPreferencesKey("id_token")
    val USER_ID = stringPreferencesKey("user_id")
    val EMAIL = stringPreferencesKey("email")
    val DISPLAY_NAME = stringPreferencesKey("display_name")
    val PROFILE_PICTURE_URL = stringPreferencesKey("profile_picture_url")
}