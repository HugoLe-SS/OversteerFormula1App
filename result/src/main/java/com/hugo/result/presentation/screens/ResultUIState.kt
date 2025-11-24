package com.hugo.result.presentation.screens

import androidx.paging.PagingData
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.utilities.AppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ResultUIState(
    val isLoading: Boolean = true,
    val error: AppError? = null,

    val driverRaceResults: Flow<PagingData<DriverRaceResultsInfo>> = emptyFlow(),
    val driverQualifyingResults: List<DriverQualifyingResultsInfo> = emptyList(),

    val constructorRaceResults: Flow<PagingData<ConstructorRaceResultsInfo>> = emptyFlow(),
    val constructorQualifyingResults: List<ConstructorQualifyingResultsInfo> = emptyList(),

    val f1CalendarResult: List<F1CalendarRaceResult> = emptyList(),

    )