package com.hugo.design.components

import com.hugo.design.R
import com.hugo.utilities.com.hugo.utilities.Navigation.Screen

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val screen: Screen,
)

object Constants {
    val bottomNavItems = listOf(
        BottomNavItem("Home", R.drawable.ic_home, Screen.HomeScreen),
        BottomNavItem("Schedule", R.drawable.ic_calendar, Screen.ScheduleScreen),
        BottomNavItem("Standings", R.drawable.ic_trophy, Screen.StandingsScreen)
    )
}
