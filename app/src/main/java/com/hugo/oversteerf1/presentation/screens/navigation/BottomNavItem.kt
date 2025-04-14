package com.hugo.oversteerf1.presentation.screens.navigation

import com.hugo.oversteerf1.R

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