package com.hugo.standings.presentation.screens.Details

sealed class StandingsDetailsEvent {
    data class RetryFetch(val driverId: String?= null, val constructorId: String?= null) : StandingsDetailsEvent()

}