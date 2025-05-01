package com.hugo.standings.domain.repository

import com.hugo.standings.domain.model.ConstructorDetails
import com.hugo.standings.domain.model.ConstructorQualifyingResultsInfo
import com.hugo.standings.domain.model.ConstructorRaceResultsInfo
import com.hugo.standings.domain.model.ConstructorStandingsInfo
import com.hugo.standings.domain.model.DriverDetails
import com.hugo.standings.domain.model.DriverQualifyingResultsInfo
import com.hugo.standings.domain.model.DriverRaceResultsInfo
import com.hugo.standings.domain.model.DriverStandingsInfo
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1StandingsRepository{

    fun getConstructorStandings(season: String): Flow<Resource<List<ConstructorStandingsInfo>>>

    fun getDriverStandings(season: String): Flow<Resource<List<DriverStandingsInfo>>>

    fun getDriverRaceResults(season: String, driverId: String): Flow<Resource<List<DriverRaceResultsInfo>>>

    fun getDriverQualifyingResults(season: String, driverId: String): Flow<Resource<List<DriverQualifyingResultsInfo>>>

    fun getConstructorRaceResults(season: String, driverId: String): Flow<Resource<List<ConstructorRaceResultsInfo>>>

    fun getConstructorQualifyingResults(season: String, driverId: String): Flow<Resource<List<ConstructorQualifyingResultsInfo>>>

    //supabase
    fun getF1ConstructorDetails(constructorId: String): Flow<Resource<ConstructorDetails?>>

    fun getF1DriverDetails(driverId: String): Flow<Resource<DriverDetails?>>

}