package com.hugo.utilities

import java.time.LocalDate
import java.time.format.DateTimeFormatter

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


    fun parseDate(dateString: String): Triple<Int, Int, Int> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)

        val year = date.year
        val month = date.monthValue
        val day = date.dayOfMonth

        return Triple(year, month, day)
    }


}

