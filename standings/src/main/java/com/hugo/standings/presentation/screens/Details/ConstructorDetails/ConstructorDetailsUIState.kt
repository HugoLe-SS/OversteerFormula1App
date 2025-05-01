package com.hugo.standings.presentation.screens.Details.ConstructorDetails

import com.hugo.standings.domain.model.ConstructorDetails
import com.hugo.standings.domain.model.ConstructorQualifyingResultsInfo
import com.hugo.standings.domain.model.ConstructorRaceResultsInfo

data class ConstructorDetailsUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val constructorRaceResults: List<ConstructorRaceResultsInfo> = emptyList(),
    val constructorQualifyingResults: List<ConstructorQualifyingResultsInfo> = emptyList(),
    val constructorDetails: ConstructorDetails? = null
)
