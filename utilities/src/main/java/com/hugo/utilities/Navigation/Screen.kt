package com.hugo.utilities.com.hugo.utilities.Navigation

import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
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
    data object ProfileScreen : Screen

    @Serializable
    data object AuthScreen : Screen

    @Serializable
    data class CalendarDetailsScreen(val info: F1CalendarInfo) : Screen

    @Serializable
    data class StandingsDetailsScreen(val constructorClickInfo: ConstructorClickInfo?= null , val driverClickInfo: DriverClickInfo?= null) : Screen

    @Serializable
    data class ResultScreen(val driverId: String?= null, val constructorId: String?= null, val circuitDetails: F1CircuitDetails?= null) : Screen

}





