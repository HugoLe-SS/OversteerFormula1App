package com.hugo.utilities

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

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


    fun String.toShortGPFormat(): String {
        return this.replace("Grand Prix", "GP").trim()
    }


}

