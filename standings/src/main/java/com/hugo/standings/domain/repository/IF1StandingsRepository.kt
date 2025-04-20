package com.hugo.standings.domain.repository

import com.hugo.standings.domain.model.ConstructorStandingsInfo
import com.hugo.standings.domain.model.DriverQualifyingResultInfo
import com.hugo.standings.domain.model.DriverRaceResultInfo
import com.hugo.standings.domain.model.DriverStandingsInfo
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1StandingsRepository{

    fun getConstructorStandings(season: String): Flow<Resource<List<ConstructorStandingsInfo>>>

    fun getDriverStandings(season: String): Flow<Resource<List<DriverStandingsInfo>>>

    fun getDriverRaceResults(season: String, driverId: String): Flow<Resource<List<DriverRaceResultInfo>>>

    fun getDriverQualifyingResults(season: String, driverId: String): Flow<Resource<List<DriverQualifyingResultInfo>>>

}