package com.hugo.utilities

sealed class  Screen (val route: String){

    data object HomeScreen : Screen(route = Route.HOME_SCREEN.name)
    data object ScheduleScreen : Screen(route = Route.SCHEDULE_SCREEN.name)
    data object StandingsScreen : Screen(route = Route.STANDINGS_SCREEN.name)
    data object NewsScreen : Screen(route = Route.NEWS_SCREEN.name)
    data object DriverDetailsScreen : Screen(route = Route.DRIVER_DETAILS_SCREEN.name)
    data object ConstructorDetailsScreen : Screen(route = Route.CONSTRUCTOR_DETAILS_SCREEN.name)
}

enum class Route(){
    HOME_SCREEN,
    SCHEDULE_SCREEN,
    STANDINGS_SCREEN,
    NEWS_SCREEN,
    DRIVER_DETAILS_SCREEN,
    CONSTRUCTOR_DETAILS_SCREEN
    //LEGACY_SCREEN
}
