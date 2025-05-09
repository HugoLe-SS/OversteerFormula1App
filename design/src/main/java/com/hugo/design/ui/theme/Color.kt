package com.hugo.design.ui.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    val primary = Color(0xFFB52400)
    val onPrimary = Color(0xFFDF3409)
    val secondary = Color(0xFF5D5F5F)
    val onSecondary = Color(0xFFFFFFFF)
    val background = Color(0xFF000000)
    val onBackground = Color(0xFF1B1B1B)

    object Teams{
        val colors: Map<String, Color> = mapOf(
            "mclaren" to Color(0xFFFF8000),
            "red_bull" to Color(0xFF3671C6),
            "ferrari" to Color(0xFFE80020),
            "mercedes" to Color(0xFF27F4D2),
            "alpine" to Color(0xFF00A1E8),
            "rb" to Color(0xFF6692FF),
            "aston_martin" to Color(0xFF229971),
            "williams" to Color(0xFF1868DB),
            "sauber" to Color(0xFF52E252),
            "haas" to Color(0xFFB6BABD)
        )
    }

    object DriverLapTimes{
        val sectorPurple: Color = Color(0xFF800080) // Best sector time
        val sectorGreen: Color = Color(0xFF008000) // Personal best sector
        val sectorYellow: Color = Color(0xFFFFFF00) // Yellow flag
        val sectorRed: Color = Color(0xFFFF0000) // Red flag
    }
}



