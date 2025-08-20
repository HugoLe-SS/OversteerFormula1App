package com.hugo.notifications.notifications.Remote.Utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth

object NotificationUtils {


    fun createNotificationChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //val channelId = channelId
            val channelName = "FCM Notifications"
            val channelDescription = "Default channel for FCM messages"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    suspend fun ensureAnonymousUser(supabaseClient: SupabaseClient) {
        val currentUser = supabaseClient.auth.currentUserOrNull()
        val isAnonymous = currentUser?.identities?.isEmpty() == true

        if (currentUser == null) {
            Log.d("FCM", "No current user, signing in anonymously.")
            try {
                supabaseClient.auth.signInAnonymously()
                Log.d("FCM", "Signed in anonymously. New UID: ${supabaseClient.auth.currentUserOrNull()?.id}")
            } catch (e: Exception) {
                Log.e("FCM", "Error signing in anonymously", e)
            }
        } else {
            Log.d("FCM", "User already exists (anonymous=$isAnonymous). UID: ${currentUser.id}")
        }
    }


}