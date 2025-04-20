package com.hugo.standings.presentation.screens.Details.DriverDetails

import com.hugo.standings.domain.model.DriverQualifyingResultInfo
import com.hugo.standings.domain.model.DriverRaceResultInfo

data class DriverDetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val driverRaceResults: List<DriverRaceResultInfo>? = emptyList(),
    val driverQualifyingResults: List<DriverQualifyingResultInfo>? = emptyList(),
)
