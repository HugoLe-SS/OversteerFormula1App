package com.hugo.standings.presentation.screens.Home

sealed class StandingsEvent {
    data object ToggleStandingsType : StandingsEvent()
    data class SetStandingsType(val type: StandingsType) : StandingsEvent()
    data object RetryFetch : StandingsEvent()
    data object Refresh : StandingsEvent()
}
