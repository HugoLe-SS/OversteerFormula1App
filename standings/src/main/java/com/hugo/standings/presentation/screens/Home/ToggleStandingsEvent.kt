package com.hugo.standings.presentation.screens.Home

sealed class ToggleStandingsEvent {
    object ToggleStandingsType : ToggleStandingsEvent()
    //data class LoadSeason(val season: String) : ToggleStandingsEvent() letting user to which season
    data class SetStandingsType(val type: StandingsType) : ToggleStandingsEvent()
}