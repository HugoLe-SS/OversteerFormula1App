package com.hugo.standings.domain.repository

import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1StandingsRepository{

    fun getConstructorStandings(season: String): Flow<Resource<List<ConstructorStandingsInfo>, AppError>>

    fun getDriverStandings(season: String): Flow<Resource<List<DriverStandingsInfo>, AppError>>

    //supabase
    fun getF1ConstructorDetails(constructorId: String): Flow<Resource<ConstructorDetails?, AppError>>

    fun getF1DriverDetails(driverId: String): Flow<Resource<DriverDetails?, AppError>>

}