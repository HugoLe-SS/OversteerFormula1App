package com.hugo.notifications.data

import android.util.Log
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.messaging.FirebaseMessaging
import com.hugo.datasource.local.PreferenceKeys
import com.hugo.datasource.local.UserPreferences
import com.hugo.notifications.domain.NotificationRepository
import com.hugo.notifications.domain.NotificationSettingType
import com.hugo.utilities.logging.AppLogger
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val auth: Auth,
    private val userPreferences: UserPreferences
): NotificationRepository {
    override suspend fun syncFcmToken(): Result<Unit> {
        return try {
            // 1. Get the current user ID. Exit if not logged in.
            val userId = auth.currentUserOrNull()?.id ?: run {
                AppLogger.d(message = "User not logged in, cannot sync FCM token.")
                return Result.success(Unit) // It's not a failure, just nothing to do.
            }

            // 2. Fetch the FCM token from Firebase using the Coroutines extension.
            val token = FirebaseMessaging.getInstance().token.await()

            // 3. Save the token to local DataStore preferences.
            userPreferences.saveFcmToken(token)

            // 4. Upsert the token to the Supabase 'DeviceToken' table,
            // associating it with the current user.
            postgrest.from("DeviceToken").upsert(
                buildJsonObject {
                    put("fcm_token", token)
                    put("user_id", userId)
                    put("platform", "Android")
                }
            ) {
                // --- THIS IS THE CORRECTED LINE ---
                // You assign the column name to the 'onConflict' property.
                this.onConflict = "fcm_token"
            }

            AppLogger.d(message =  "FCM Token synced successfully for user $userId")
            Result.success(Unit)

        } catch (e: Exception) {
            AppLogger.e(message =  "Failed to sync FCM token" + e.message)
            Result.failure(e)
        }
    }

    val TAG = "NotifSettings"

    override suspend fun updateNotificationSetting(setting: NotificationSettingType, isEnabled: Boolean): Result<Unit> {
        return try {
            // Step 1: Get the current device's FCM token from local storage.
            // We use this to identify which row to update in the Supabase table.
            val fcmToken = userPreferences.getFcmToken()
                ?: throw IllegalStateException("FCM Token not found, cannot update settings.")

            // Step 2: Determine which column name in Supabase corresponds to the setting.
            val remoteColumnName = when (setting) {
                NotificationSettingType.RACE_STARTS -> "notif_race_starts"
                NotificationSettingType.ALL_SESSIONS -> "notif_all_sessions"
                NotificationSettingType.BREAKING_NEWS -> "notif_breaking_news"
                NotificationSettingType.GENERAL -> "notif_general"
            }

            // Step 3: Update the row in the Supabase "DeviceToken" table.
            Log.d(TAG, "Updating remote setting: $remoteColumnName to $isEnabled for token: $fcmToken")
            postgrest.from("DeviceToken")
                .update( // The data to update
                    buildJsonObject { put(remoteColumnName, isEnabled) }
                ) {
                    filter { // The WHERE clause
                        eq("fcm_token", fcmToken)
                    }
                }

            // Step 4: If the remote update succeeds, update the local DataStore preference.
            val localKey = getLocalKey(setting)
            userPreferences.updateBoolean(localKey, isEnabled)

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating notification setting", e)
            Result.failure(e)
        }
    }


    private fun getRemoteColumn(setting: NotificationSettingType) = when (setting) {
        NotificationSettingType.RACE_STARTS -> "notif_race_starts"
        NotificationSettingType.ALL_SESSIONS -> "notif_all_sessions"
        NotificationSettingType.BREAKING_NEWS -> "notif_breaking_news"
        NotificationSettingType.GENERAL -> "notif_general"
    }

    private fun getLocalKey(setting: NotificationSettingType): Preferences.Key<Boolean> {
        return when (setting) {
            NotificationSettingType.RACE_STARTS -> PreferenceKeys.NOTIF_RACE_STARTS
            NotificationSettingType.ALL_SESSIONS -> PreferenceKeys.NOTIF_ALL_SESSIONS
            NotificationSettingType.BREAKING_NEWS -> PreferenceKeys.NOTIF_BREAKING_NEWS
            NotificationSettingType.GENERAL -> PreferenceKeys.NOTIF_GENERAL
        }
    }
}