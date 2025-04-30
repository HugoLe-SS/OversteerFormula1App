package com.hugo.utilities.com.hugo.utilities.Navigation

import kotlinx.serialization.Serializable


sealed interface Screen {
    @Serializable
    object HomeScreen : Screen

    @Serializable
    object ScheduleScreen : Screen

    @Serializable
    object StandingsScreen : Screen

//    @Serializable
//    data class CalendarResultScreen(val round: String) : Screen

    @Serializable
    data class CalendarResultScreen(val info: CalendarClickInfo) : Screen

    @Serializable
    data class ConstructorDetailsScreen(val constructorId: String) : Screen

    @Serializable
    data class DriverDetailsScreen(val driverId: String) : Screen
}




