package com.hugo.oversteerf1.notifications.Local

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hugo.oversteerf1.presentation.activity.MainActivity
import com.hugo.utilities.logging.AppLogger

class RaceNotificationReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 101 // Unique ID for this type of notification
        const val CHANNEL_ID = "race_start_reminders"
        const val EXTRA_RACE_NAME = "extra_race_name"
        const val EXTRA_RACE_TIME_MILLIS = "extra_race_time_millis"
        const val EXTRA_EVENT_ROUND = "extra_event_round" // To identify the specific event
    }

    override fun onReceive(context: Context, intent: Intent) {
        AppLogger.d(message = "RaceNotificationReceiver: Alarm received!")
        val raceName = intent.getStringExtra(EXTRA_RACE_NAME) ?: "Upcoming F1 Race"
        val eventRound = intent.getIntExtra(EXTRA_EVENT_ROUND, -1) // Get round to make intent unique

        // Create an intent to open the app when the notification is tapped
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // You can add extras to navigate to a specific screen if needed
            // putExtra("navigateTo", "schedule_detail_screen")
            // putExtra("round", eventRound)
        }
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val contentIntent = PendingIntent.getActivity(context, eventRound, openAppIntent, pendingIntentFlags)


        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(com.hugo.design.R.drawable.ic_noti) // Replace with your notification icon
            .setContentTitle("$raceName Starting Soon!")
            .setContentText("The main race is about to begin in approximately 2 hours.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) // Dismiss notification when tapped
            .setContentIntent(contentIntent) // Action when notification is tapped
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Show on lock screen

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            try {
                notify(NOTIFICATION_ID + eventRound, builder.build()) // Use unique ID per event
                AppLogger.d(message = "RaceNotificationReceiver: Notification displayed for $raceName.")
            } catch (e: SecurityException) {
                // This can happen on Android 13+ if POST_NOTIFICATIONS is denied
                AppLogger.e(message = "RaceNotificationReceiver: SecurityException - Missing POST_NOTIFICATIONS permission. ${e.message}")
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Race Reminders"
            val descriptionText = "Notifications for upcoming F1 race starts"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            AppLogger.d(message = "RaceNotificationReceiver: Notification channel created/ensured.")
        }
    }
}