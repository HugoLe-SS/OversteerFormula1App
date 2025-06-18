package com.hugo.oversteerf1.notifications.Local

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.utilities.AppUtilities
import com.hugo.utilities.logging.AppLogger
import java.util.Calendar

object NotificationScheduler {

    private const val TWO_HOURS_IN_MILLIS = 2 * 60 * 60 * 1000L

    fun scheduleRaceReminder(context: Context, raceEvent: F1CalendarInfo) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // 1. Calculate Race Start Time
        val raceStartTimeMillis = AppUtilities.convertToMillis(
            date = raceEvent.mainRaceDate ?: return, // Need valid date/time
            time = raceEvent.mainRaceTime ?: return
        )
        if (raceStartTimeMillis == 0L) {
            AppLogger.e(message = "NotificationScheduler: Invalid race start time for ${raceEvent.raceName}. Cannot schedule.")
            return
        }

        // 2. Calculate Notification Time (2 hours before race start)
        val notificationTimeMillis = raceStartTimeMillis - TWO_HOURS_IN_MILLIS

        // 3. Check if notification time is in the future
        if (notificationTimeMillis <= System.currentTimeMillis()) {
            AppLogger.d(message = "NotificationScheduler: Notification time for ${raceEvent.raceName} is in the past. Not scheduling.")
            return
        }

        // 4. Create Intent for BroadcastReceiver
        val intent = Intent(context, RaceNotificationReceiver::class.java).apply {
            putExtra(RaceNotificationReceiver.EXTRA_RACE_NAME, raceEvent.raceName)
            putExtra(RaceNotificationReceiver.EXTRA_RACE_TIME_MILLIS, raceStartTimeMillis)
            putExtra(RaceNotificationReceiver.EXTRA_EVENT_ROUND, raceEvent.round ?: raceEvent.hashCode()) // Unique request code per event
        }

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        // Use event.round or another unique ID for the request code to allow multiple alarms
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            raceEvent.round.toInt() ?: raceEvent.hashCode(), // Unique request code
            intent,
            pendingIntentFlags
        )

        // 5. Schedule the Alarm
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                AppLogger.e(message = "NotificationScheduler: Cannot schedule exact alarms. User needs to grant permission.")
                // TODO: Guide user to settings to grant SCHEDULE_EXACT_ALARM or use setAndAllowWhileIdle()
                // For now, try a less exact alarm as a fallback if allowed
                // alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notificationTimeMillis, pendingIntent)
                return // Or handle this more gracefully
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notificationTimeMillis, pendingIntent)
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTimeMillis, pendingIntent)
            }
            AppLogger.d(message = "NotificationScheduler: Reminder scheduled for ${raceEvent.raceName} at ${Calendar.getInstance().apply { timeInMillis = notificationTimeMillis }.time}")
        } catch (se: SecurityException) {
            AppLogger.e(message = "NotificationScheduler: SecurityException while scheduling alarm. ${se.message}")
            // This can happen if SCHEDULE_EXACT_ALARM is not granted or revoked.
        }
    }

    fun cancelRaceReminder(context: Context, raceEvent: F1CalendarInfo) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, RaceNotificationReceiver::class.java) // Intent must match the one used for scheduling

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE // Use NO_CREATE to check if it exists
        } else {
            PendingIntent.FLAG_NO_CREATE
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            raceEvent.round.toInt() ?: raceEvent.hashCode(), // Must be the same request code
            intent,
            pendingIntentFlags
        )

        if (pendingIntent != null) { // Only cancel if it was actually scheduled
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel() // Also cancel the PendingIntent itself
            AppLogger.d(message = "NotificationScheduler: Reminder cancelled for ${raceEvent.raceName}")
        }
    }

    // Function to schedule reminders for a list of upcoming events
    fun scheduleRemindersForUpcomingRaces(context: Context, upcomingRaces: List<F1CalendarInfo>) {
        AppLogger.d(message = "NotificationScheduler: Scheduling reminders for ${upcomingRaces.size} races.")
        // Optional: Clear all previously scheduled race reminders first to avoid duplicates if list changes
        // clearAllRaceReminders(context, oldListOfRaceEvents)
        upcomingRaces.forEach { raceEvent ->
            scheduleRaceReminder(context, raceEvent)
        }
    }
}