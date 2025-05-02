package com.hugo.standings.domain.repository

import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1StandingsRepository{

    fun getConstructorStandings(season: String): Flow<Resource<List<ConstructorStandingsInfo>>>

    fun getDriverStandings(season: String): Flow<Resource<List<DriverStandingsInfo>>>

    fun getDriverRaceResults(season: String, driverId: String): Flow<Resource<List<DriverRaceResultsInfo>>>

    fun getDriverQualifyingResults(season: String, driverId: String): Flow<Resource<List<DriverQualifyingResultsInfo>>>

    fun getConstructorRaceResults(season: String, constructorId: String): Flow<Resource<List<ConstructorRaceResultsInfo>>>

    fun getConstructorQualifyingResults(season: String, constructorId: String): Flow<Resource<List<ConstructorQualifyingResultsInfo>>>

    //supabase
    fun getF1ConstructorDetails(constructorId: String): Flow<Resource<ConstructorDetails?>>

    fun getF1DriverDetails(driverId: String): Flow<Resource<DriverDetails?>>

}