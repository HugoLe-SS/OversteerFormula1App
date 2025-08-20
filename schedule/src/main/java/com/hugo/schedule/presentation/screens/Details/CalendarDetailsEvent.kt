package com.hugo.schedule.presentation.screens.Details

sealed class CalendarDetailsEvent {
    data class RetryFetch(val circuitId: String) : CalendarDetailsEvent()
}