package com.hugo.utilities.com.hugo.utilities

object AppLaunchManager {
    var hasFetchedDriverStandings = false
    var hasFetchedConstructorStandings = false
    val fetchedDriverDetails = mutableSetOf<String>()
    var fetchedConstructorDetails = mutableSetOf<String>()

    var hasFetchedCalendar = false
    val fetchedCircuitDetails = mutableSetOf<String>()

}