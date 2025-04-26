package com.hugo.schedule.presentation.screens.Details

import com.hugo.schedule.domain.model.F1CalendarResult
import com.hugo.utilities.Resource

data class CalendarResultUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val f1CalendarResult: List<F1CalendarResult> = emptyList(),
)