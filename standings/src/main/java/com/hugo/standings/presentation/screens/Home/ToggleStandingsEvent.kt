package com.hugo.standings.presentation.screens.Home

sealed class ToggleStandingsEvent {
    object ToggleStandingsType : ToggleStandingsEvent()
    data class SetStandingsType(val type: StandingsType) : ToggleStandingsEvent()
}
