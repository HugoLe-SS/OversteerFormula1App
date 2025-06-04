package com.hugo.schedule.presentation.screens.Home

sealed class ScheduleEvent {
    data object ToggleSchedule : ScheduleEvent()
    data class SetScheduleType(val type: ScheduleType) : ScheduleEvent()
    data object RetryFetch : ScheduleEvent()
    data object Refresh : ScheduleEvent()
}