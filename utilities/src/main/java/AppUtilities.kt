package com.hugo.utilities

import android.content.Context
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object AppUtilities {
    fun calculatePositionChange(grid: String, position: String): String {
        val gridValue = grid.toInt()
        val positionValue = position.toInt()
        val difference = gridValue - positionValue

        return if (difference < 0) {
            "${-difference}"
        } else {
            "${difference}"
        }
    }


    data class DateInfo(
        val year: String,
        val monthShort: String,
        val monthFull: String,
        val day: String
    )

    data class CountDownInfo(
        val sessionName: String,
        val days: String,
        val hours: String,
        val minutes: String,
        val status: String?,
        val progress: Float = 0f
    )

    data class Session(
        val name: String,
        val date: String?,
        val time: String?,
        val sessionDuration: Int
    )

    // Readable Date
    fun parseDate(dateString: String?): DateInfo? {
        if (dateString.isNullOrBlank()) return null

        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(dateString, formatter)

            DateInfo(
                year = date.year.toString(),
                monthShort = date.month.getDisplayName(java.time.format.TextStyle.SHORT, Locale.ENGLISH),
                monthFull = date.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH),
                day = date.dayOfMonth.toString()
            )
        } catch (e: DateTimeParseException) {
            null
        }
    }

    // Local Time
    fun formatToLocalTime(timeString: String?, targetTimeZone: String = ZoneId.systemDefault().id): String {
        if (timeString.isNullOrEmpty()) return "N/A"

        return try {
            val utcTime = LocalTime.parse(
                timeString.trim().removeSuffix("Z"),
                DateTimeFormatter.ofPattern("HH:mm:ss")
            ).atDate(LocalDate.now()).atZone(ZoneOffset.UTC)

            val localTime = utcTime.withZoneSameInstant(ZoneId.of(targetTimeZone))
            localTime.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault()))
        } catch (e: Exception) {
            "Invalid Time"
        }
    }

    // date and time to milliseconds
    fun convertToMillis(date: String, time: String): Long {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val cleanedTime = time.replace("Z", "")
            dateFormat.parse("$date $cleanedTime")?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }


    fun formatToDaysHoursMinutes(
        name: String?,
        date: String?,
        time: String?,
        sessionDuration: Int? = 60
    ): CountDownInfo? {
        if (date.isNullOrBlank() || time.isNullOrBlank()) return null

        val startMillis = convertToMillis(date, time)
        val durationMillis = (sessionDuration ?: 60) * 60 * 1000L
        val endMillis = startMillis + durationMillis
        val currentMillis = System.currentTimeMillis()

        val status = when {
            currentMillis in startMillis until endMillis -> "Live"
            currentMillis >= endMillis -> "Finished"
            else -> "Upcoming"
        }

        val millisLeft = startMillis - currentMillis

        val progress = when (status) {
            "Live" -> {
                val elapsed = currentMillis - startMillis
                (elapsed.toFloat() / durationMillis).coerceIn(0f, 1f)
            }
            else -> 0f
        }

        if (status != "Upcoming" || millisLeft <= 0) {
            return CountDownInfo(sessionName = name?:"", days = "0", hours = "0", minutes = "0", status = status, progress = progress)
        }

        val totalMinutes = TimeUnit.MILLISECONDS.toMinutes(millisLeft)
        val days = totalMinutes / (60 * 24)
        val hours = (totalMinutes % (60 * 24)) / 60
        val minutes = totalMinutes % 60

        return CountDownInfo(
            sessionName = name?:"" ,
            days = days.toString(),
            hours = hours.toString(),
            minutes = minutes.toString(),
            status = status,
            progress = progress
        )
    }



    fun getNextUpcomingSession(sessions: List<Session>): Session? {
        val now = System.currentTimeMillis()

        return sessions
            .mapNotNull { session ->
                val start = AppUtilities.convertToMillis(session.date ?: "", session.time ?: "")
                val end = start + (session.sessionDuration * 60 * 1000L)

                when {
                    now in start until end -> session to 0L // Session is Live — prioritize
                    now < start -> session to start        // Session is upcoming
                    else -> null                            // Finished sessions are ignored
                }
            }
            .minByOrNull { it.second }
            ?.first
    }

    fun String.toShortGPFormat(): String {
        return this.replace("Grand Prix", "GP").trim()
    }

    fun String.toFlagName(): Int {
        return "R.drawable.flag_${this.lowercase()}".toInt()
    }


    fun getFlagResId(context: Context, countryCode: String): Int {
        val flagName = "flag_${countryCode.lowercase()}"
        val flagResId = context.resources.getIdentifier(flagName, "drawable", context.packageName)
        return flagResId
    }




}

