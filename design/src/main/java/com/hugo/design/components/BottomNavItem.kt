package com.hugo.design.components

import com.hugo.design.R
import com.hugo.utilities.Screen

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Home", R.drawable.ic_home, Screen.HomeScreen.route),
    BottomNavItem("Schedule", R.drawable.ic_calendar, Screen.ScheduleScreen.route),
    BottomNavItem("Standings", R.drawable.ic_trophy, Screen.StandingsScreen.route),
    BottomNavItem("News", R.drawable.ic_news, Screen.NewsScreen.route)
)