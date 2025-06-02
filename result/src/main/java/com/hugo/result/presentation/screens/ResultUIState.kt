package com.hugo.result.presentation.screens

import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.utilities.AppError

data class ResultUIState(
    val isLoading: Boolean = true,
    val error: AppError? = null,

    val driverRaceResults: List<DriverRaceResultsInfo> = emptyList(),
    val driverQualifyingResults: List<DriverQualifyingResultsInfo> = emptyList(),

    val constructorRaceResults: List<ConstructorRaceResultsInfo> = emptyList(),
    val constructorQualifyingResults: List<ConstructorQualifyingResultsInfo> = emptyList(),

    val f1CalendarResult: List<F1CalendarRaceResult> = emptyList(),

    )