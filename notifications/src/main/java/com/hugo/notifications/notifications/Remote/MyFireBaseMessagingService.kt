package com.hugo.notifications.notifications.Remote

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hugo.notifications.domain.NotificationRepository
import com.hugo.notifications.notifications.Remote.Utils.NotificationUtils
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    companion object {
        private const val TAG = "FCM"
        const val RACE_REMINDER_CHANNEL_ID = "f1_race_reminders_channel"
        const val RACE_REMINDER_NOTIFICATION_ID_BASE = 1000 // Base for dynamic notification IDs
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token received. Triggering sync.")

        // Delegate all complex logic to the repository
        serviceScope.launch {
            notificationRepository.syncFcmToken()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        AppLogger.d(TAG, "FCM Message From: ${remoteMessage.from}")

        // Case 1: Message contains a data payload (sent from your server)
        // This is preferred as your app has full control over the notification.
        if (remoteMessage.data.isNotEmpty()) {
            AppLogger.d(TAG, "Message data payload: ${remoteMessage.data}")

            val title = remoteMessage.data["title"] ?: "Default title" // Default title
            val body = remoteMessage.data["body"] ?: "You have a new F1 update!"   // Default body
            val type = remoteMessage.data["type"] // Custom type you send from server
            val raceId = remoteMessage.data["raceId"]

            AppLogger.d(TAG, "Received Data Message: Title='$title', Body='$body', Type='$type', RaceId='$raceId'")

            // Here you can customize notification based on 'type' or other data
            sendNotification(title, body, raceId)
        }

        // Case 2: Message contains a notification payload (often sent from Firebase console or simple server setups)
        // If the app is in the background or killed, the system handles displaying this notification automatically.
        // If the app is in the foreground, this callback is still triggered.
        remoteMessage.notification?.let { notification ->
            AppLogger.d(TAG, "Message Notification Payload: Title='${notification.title}', Body='${notification.body}'")
            sendNotification(
                notification.title ?: "Default title",
                notification.body ?: "Check for F1 updates!",
                null // No specific raceId from notification payload by default
            )
        }
    }


    private fun sendNotification(title: String, messageBody: String, raceId: String?) {
        // Intent to launch MainActivity when notification is tapped
        val intent = Intent("com.hugo.oversteerf1.OPEN_MAIN_ACTIVITY")
        intent.setPackage(packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Ensures not too many instances of MainActivity
        // Optional: Add extras to the intent if you want to deep-link or pass data
        if (raceId != null) {
            intent.putExtra("notification_race_id", raceId)
            intent.putExtra("navigateTo", "race_details_from_notification") // Custom action
        }

        val requestCode = raceId?.hashCode() ?: System.currentTimeMillis().toInt() // Unique request code for PendingIntent
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_ONE_SHOT
        }
        val pendingIntent = PendingIntent.getActivity(this, requestCode, intent, pendingIntentFlags)

        // Create the notification channel (should be done on app startup, but good to ensure here too)
        NotificationUtils.createNotificationChannel(this, RACE_REMINDER_CHANNEL_ID)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, RACE_REMINDER_CHANNEL_ID)
            .setSmallIcon(com.hugo.design.R.drawable.ic_checkered_flag)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = RACE_REMINDER_NOTIFICATION_ID_BASE + (raceId?.hashCode() ?: title.hashCode())

        try {
            notificationManager.notify(notificationId, notificationBuilder.build())
            AppLogger.d(TAG, "Notification sent: ID=$notificationId, Title='$title'")
        } catch (e: SecurityException) {
            AppLogger.e(TAG, "SecurityException while sending notification. POST_NOTIFICATIONS permission likely missing or denied. ${e.message}")
        }
    }

}