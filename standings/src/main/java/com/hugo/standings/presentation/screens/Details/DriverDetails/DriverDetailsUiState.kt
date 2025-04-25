package com.hugo.standings.presentation.screens.Details.DriverDetails

import com.hugo.standings.domain.model.DriverQualifyingResultsInfo
import com.hugo.standings.domain.model.DriverRaceResultsInfo

data class DriverDetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val driverRaceResults: List<DriverRaceResultsInfo> = emptyList(),
    val driverQualifyingResults: List<DriverQualifyingResultsInfo> = emptyList(),
)
