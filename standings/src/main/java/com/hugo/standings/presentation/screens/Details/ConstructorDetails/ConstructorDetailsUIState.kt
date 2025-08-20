package com.hugo.standings.presentation.screens.Details.ConstructorDetails

import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo

data class ConstructorDetailsUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val constructorRaceResults: List<ConstructorRaceResultsInfo> = emptyList(),
    val constructorQualifyingResults: List<ConstructorQualifyingResultsInfo> = emptyList(),
    val constructorDetails: ConstructorDetails? = null
)
