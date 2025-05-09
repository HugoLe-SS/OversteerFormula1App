package com.hugo.result.domain.repository

import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1ResultRepository {
    fun getDriverRaceResults(season: String, driverId: String): Flow<Resource<List<DriverRaceResultsInfo>>>

    fun getDriverQualifyingResults(season: String, driverId: String): Flow<Resource<List<DriverQualifyingResultsInfo>>>

    fun getConstructorRaceResults(season: String, constructorId: String): Flow<Resource<List<ConstructorRaceResultsInfo>>>

    fun getConstructorQualifyingResults(season: String, constructorId: String): Flow<Resource<List<ConstructorQualifyingResultsInfo>>>
}