package com.hugo.result.domain.repository

import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1ResultRepository {
    fun getDriverRaceResults(season: String, driverId: String): Flow<Resource<List<DriverRaceResultsInfo>, AppError>>

    fun getDriverQualifyingResults(season: String, driverId: String): Flow<Resource<List<DriverQualifyingResultsInfo>, AppError>>

    fun getConstructorRaceResults(season: String, constructorId: String): Flow<Resource<List<ConstructorRaceResultsInfo>, AppError>>

    fun getConstructorQualifyingResults(season: String, constructorId: String): Flow<Resource<List<ConstructorQualifyingResultsInfo>, AppError>>

    fun getF1CalendarResult(season: String, circuitId: String): Flow<Resource<List<F1CalendarRaceResult>, AppError>>
}