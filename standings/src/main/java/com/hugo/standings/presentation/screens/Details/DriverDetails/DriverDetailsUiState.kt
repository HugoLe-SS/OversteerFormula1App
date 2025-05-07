package com.hugo.standings.presentation.screens.Details.DriverDetails

import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo

data class DriverDetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,

    val driverRaceResults: List<DriverRaceResultsInfo> = emptyList(),
    val driverQualifyingResults: List<DriverQualifyingResultsInfo> = emptyList(),
    val driverDetails: DriverDetails? = null,

    val constructorRaceResults: List<ConstructorRaceResultsInfo> = emptyList(),
    val constructorQualifyingResults: List<ConstructorQualifyingResultsInfo> = emptyList(),
    val constructorDetails: ConstructorDetails? = null
)
