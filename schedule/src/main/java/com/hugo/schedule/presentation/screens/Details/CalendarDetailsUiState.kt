package com.hugo.schedule.presentation.screens.Details

import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails

data class CalendarDetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val f1CalendarResult: List<F1CalendarRaceResult> = emptyList(),
    val f1CircuitDetails: F1CircuitDetails? = null
)