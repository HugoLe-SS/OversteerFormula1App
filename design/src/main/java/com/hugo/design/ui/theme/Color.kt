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

        object Circuit {
            val colors: Map<String, Color> = mapOf(
                "North America" to Color(0xFF008000),
                "South America" to Color(0xFF7F00FF),
                "Asia" to Color(0xFF0000FF),
                "Africa" to Color(0xFFFFFF00),
                "Europe" to Color(0xFFB31B1B),
                "Oceania" to Color(0xFFFF8000),
            )

            val circuitContinentMap: Map<String, String> = mapOf(
                "albert_park" to "Oceania",
                "shanghai" to "Asia",
                "suzuka" to "Asia",
                "bahrain" to "Asia",
                "jeddah" to "Asia",
                "miami" to "North America",
                "imola" to "Europe",
                "monaco" to "Europe",
                "catalunya" to "Europe",
                "villeneuve" to "North America",
                "red_bull_ring" to "Europe",
                "silverstone" to "Europe",
                "spa" to "Europe",
                "hungaroring" to "Europe",
                "zandvoort" to "Europe",
                "monza" to "Europe",
                "baku" to "Asia",
                "marina_bay" to "Asia",
                "americas" to "North America",
                "rodriguez" to "South America",
                "interlagos" to "South America",
                "vegas" to "North America",
                "losail" to "Asia",
                "yas_marina" to "Asia",
            )
        }




    object DriverLapTimes{
        val sectorPurple: Color = Color(0xFF800080) // Best sector time
        val sectorGreen: Color = Color(0xFF008000) // Personal best sector
        val sectorYellow: Color = Color(0xFFFFFF00) // Yellow flag
        val sectorRed: Color = Color(0xFFFF0000) // Red flag
    }
}



