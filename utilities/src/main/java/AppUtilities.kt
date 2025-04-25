package com.hugo.utilities

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

}