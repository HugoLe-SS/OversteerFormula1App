package com.hugo.schedule.presentation.screens.Home

import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo

data class ScheduleHomeUiState(
    val isLoading: Boolean = true,
    val error: String?= null,
    val f1Calendar: List<F1CalendarInfo> = emptyList(),

    )
