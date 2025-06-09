package com.hugo.utilities.com.hugo.utilities

object AppLaunchManager {

    var hasFetchedHomeDetails = false

    var hasFetchedDriverStandings = false
    var hasFetchedConstructorStandings = false
    val fetchedDriverDetails = mutableSetOf<String>()
    var fetchedConstructorDetails = mutableSetOf<String>()

    var hasFetchedCalendar = false
    val fetchedCircuitDetails = mutableSetOf<String>()

    var hasFetchedConstructorResults = mutableSetOf<String>()
    var hasFetchedDriverResults = mutableSetOf<String>()
    var hasFetchedCalendarResults = mutableSetOf<String>()

}