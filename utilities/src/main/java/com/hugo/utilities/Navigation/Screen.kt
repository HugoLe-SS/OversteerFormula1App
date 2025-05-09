package com.hugo.utilities.com.hugo.utilities.Navigation

import com.hugo.utilities.com.hugo.utilities.Navigation.model.CalendarClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo
import kotlinx.serialization.Serializable


sealed interface Screen {
    @Serializable
    data object HomeScreen : Screen

    @Serializable
    data object ScheduleScreen : Screen

    @Serializable
    data object StandingsScreen : Screen

    @Serializable
    data class CalendarResultScreen(val info: CalendarClickInfo) : Screen

    @Serializable
    data class StandingsDetailsScreen(val constructorClickInfo: ConstructorClickInfo?= null , val driverClickInfo: DriverClickInfo?= null) : Screen

    @Serializable
    data class ResultScreen(val driverId: String?= null, val constructorId: String?= null) : Screen

}




