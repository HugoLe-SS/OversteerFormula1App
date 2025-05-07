package com.hugo.utilities.com.hugo.utilities.Navigation

import kotlinx.serialization.Serializable


sealed interface Screen {
    @Serializable
    object HomeScreen : Screen

    @Serializable
    object ScheduleScreen : Screen

    @Serializable
    object StandingsScreen : Screen

    @Serializable
    data class CalendarResultScreen(val info: CalendarClickInfo) : Screen

    @Serializable
    data class StandingsDetailsScreen(val driverId: String?= null, val constructorId: String?= null) : Screen
}




