package com.hugo.standings.data.remote

import com.hugo.standings.data.remote.dto.ConstructorStandingsDto
import com.hugo.standings.data.remote.dto.DriverStandingsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface F1StandingsApi {
    //Constructor Standings API
    @GET("ergast/f1/{season}/constructorstandings")
    suspend fun getConstructorStandings(
        @Path("season") season: String
    ): ConstructorStandingsDto
    //Driver Standings API
    @GET("ergast/f1/{season}/driverstandings")
    suspend fun getDriverStandings(
        @Path("season") season: String
    ): DriverStandingsDto
}