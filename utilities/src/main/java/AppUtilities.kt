package com.hugo.utilities

import android.annotation.SuppressLint
import com.hugo.utilities.com.hugo.utilities.Navigation.model.CountDownInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DateInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.Session
import com.hugo.utilities.logging.AppLogger
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object AppUtilities {
    // --- Reusable Error Mapping Function ---
    fun Throwable.toAppError(): AppError {
        AppLogger.e(message = "Mapping Throwable to AppError: ${this::class.java.simpleName} - ${this.localizedMessage}")
        return when (this) {
            is IOException -> AppError.RemoteError.NO_INTERNET_ERROR // Covers UnknownHostException, SocketTimeoutException etc.
            is retrofit2.HttpException -> { // Or your specific HTTP client's exception
                val specificErrorType = when (this.code()) {
                    400 -> AppError.RemoteError.CLIENT_ERROR // Bad Request
                    401 -> AppError.RemoteError.CLIENT_ERROR // Unauthorized - you might want a more specific AppError.AuthError here
                    403 -> AppError.RemoteError.CLIENT_ERROR // Forbidden
                    404 -> AppError.RemoteError.CLIENT_ERROR // Not Found - or AppError.NotFoundError
                    429 -> AppError.RemoteError.TOO_MANY_REQUESTS_ERROR
                    in 500..599 -> AppError.RemoteError.SERVER_ERROR
                    else -> AppError.RemoteError.UNKNOWN_ERROR
                }
                RemoteErrorWithCode(specificErrorType, this.code(), this.message())
            }
            is com.google.gson.JsonSyntaxException, // Example for serialization error
            is kotlinx.serialization.SerializationException -> AppError.RemoteError.SERIALIZATION_ERROR
            else -> {
                AppLogger.e(message = "Unhandled exception type mapped to UNKNOWN_ERROR: ${this::class.java.name}")
                AppError.RemoteError.UNKNOWN_ERROR
            }
        }
    }

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

    // Parse UTC date and time to ZonedDateTime
    fun parseUtcRaceDateTimeToZoned(mainRaceDate: String?, mainRaceTime: String?): ZonedDateTime? {
        if (mainRaceDate.isNullOrBlank() || mainRaceTime.isNullOrBlank()) return null

        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC)
            val dateTimeStr = "$mainRaceDate ${mainRaceTime.removeSuffix("Z")}"
            val localDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            localDateTime.atZone(ZoneOffset.UTC)
        } catch (e: Exception) {
            null
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

    @SuppressLint("DefaultLocale")
    fun Long.formatMillisToTime(): String {
        val hours = this / (1000 * 60 * 60)
        val minutes = (this / (1000 * 60)) % 60
        val seconds = (this / 1000) % 60
        val milliseconds = this % 1000

        return if (hours > 0) {
            String.format("%d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds)
        } else if (minutes>0) {
            String.format("%d:%02d.%03d", minutes, seconds, milliseconds)
        }
        else {
            String.format("%2d.%03d", seconds, milliseconds)
        }
    }





}

