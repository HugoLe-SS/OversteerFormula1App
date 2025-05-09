package com.hugo.result.presentation.screens

import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo

data class ResultUIState(
    val isLoading: Boolean = true,
    val error: String? = null,

    val driverRaceResults: List<DriverRaceResultsInfo> = emptyList(),
    val driverQualifyingResults: List<DriverQualifyingResultsInfo> = emptyList(),

    val constructorRaceResults: List<ConstructorRaceResultsInfo> = emptyList(),
    val constructorQualifyingResults: List<ConstructorQualifyingResultsInfo> = emptyList(),
)