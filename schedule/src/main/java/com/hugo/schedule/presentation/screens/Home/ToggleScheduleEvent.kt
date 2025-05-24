package com.hugo.schedule.presentation.screens.Home

sealed class ToggleScheduleEvent {
    object ToggleSchedule : ToggleScheduleEvent()
    data class SetScheduleType(val type: ScheduleType) : ToggleScheduleEvent()

}