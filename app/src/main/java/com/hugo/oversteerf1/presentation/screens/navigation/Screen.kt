package com.hugo.oversteerf1.presentation.screens.navigation

sealed class  Screen (val route: String){

    data object HomeScreen : Screen(route = Route.HOME_SCREEN.name)
    data object ScheduleScreen : Screen(route = Route.SCHEDULE_SCREEN.name)
    data object StandingsScreen : Screen(route = Route.STANDINGS_SCREEN.name)
    data object NewsScreen : Screen(route = Route.NEWS_SCREEN.name)
}

enum class Route(){
    HOME_SCREEN,
    SCHEDULE_SCREEN,
    STANDINGS_SCREEN,
    NEWS_SCREEN,
    //LEGACY_SCREEN
}
