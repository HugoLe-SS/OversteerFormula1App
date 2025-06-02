package com.hugo.schedule.presentation.screens.Details

import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.utilities.AppError

data class CalendarDetailsUiState(
    val isLoading: Boolean = true,
    val error: AppError? = null,
    val f1CircuitDetails: F1CircuitDetails? = null
)