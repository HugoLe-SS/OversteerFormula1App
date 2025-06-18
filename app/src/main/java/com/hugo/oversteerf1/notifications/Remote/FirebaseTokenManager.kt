package com.hugo.oversteerf1.notifications.Remote

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


object FirebaseTokenManager {

    @Serializable
    data class DeviceToken(
        val user_id: String?,
        val fcm_token: String,
        val platform: String = "Android",
        val is_active: Boolean = true,
        val race_reminder: Boolean
    )


    fun fetchAndStoreToken(
        supabaseClient: SupabaseClient,
        userId: String?,
        notificationPermissionGranted: Boolean
    ) {
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                //Log.d("FCM", "FCM Token: $token")

                if (userId != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.d("FCM", "Attempting to upsert token to Supabase. " +
                                "UserID: $userId, Token: $token, " +
                                "Permission: $notificationPermissionGranted")

                        try {
                            supabaseClient.from("DeviceToken").upsert(
                                DeviceToken(
                                    user_id = userId,
                                    fcm_token = token,
                                    race_reminder = notificationPermissionGranted
                                ),
                               {
                                   onConflict = "fcm_token"
                               }
                            )
                            Log.d("FCM", "FCM token upserted successfully.")
                        } catch (e: PostgrestRestException) {
                            val user = supabaseClient.auth.currentUserOrNull()

                            if (user?.identities?.isEmpty() == true ) {
                                Log.w("FCM", "Anonymous user - skipping token upsert. Reason: ${e.message}")
                            } else {
                                Log.e("FCM", "Failed to upsert FCM token", e)
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FCM", "Fetching FCM token failed", exception)
            }
    }


}


//                        try {
//                            supabaseClient.postgrest["DeviceToken"]
//                                .upsert(
//                                    DeviceToken(
//                                        user_id = userId,
//                                        fcm_token = token
//                                    ),
//                                    {onConflict = "fcm_token"}
//                                )
////                            val result: PostgrestResult = supabaseClient.postgrest
////                                .from("DeviceToken")
////                                .upsert(
////                                    values = listOf(DeviceToken),
////                                    {onConflict = "fcm_token"}
////                                )
//
//                            Log.d("FCM", "Token inserted to Supabase")
//                        } catch (e: Exception) {
//                            Log.e("FCM", "Insert failed", e)
//                        }