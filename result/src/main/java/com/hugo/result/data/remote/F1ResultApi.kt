package com.hugo.result.data.remote

import com.hugo.result.data.remote.dto.QualifyingResult.ConstructorQualifyingResultDto
import com.hugo.result.data.remote.dto.QualifyingResult.DriverQualifyingResultDto
import com.hugo.result.data.remote.dto.RaceResult.ConstructorRaceResultDto
import com.hugo.result.data.remote.dto.RaceResult.DriverRaceResultDto
import retrofit2.http.GET
import retrofit2.http.Path

interface F1ResultApi {
    // Driver Race result based on season and ID
    @GET("ergast/f1/{season}/drivers/{driverId}/results")
    suspend fun getDriverRaceResult(
        @Path("season") season: String,
        @Path("driverId") driverId: String
    ): DriverRaceResultDto

    // Driver Qualifying result based on season and ID
    @GET("ergast/f1/{season}/drivers/{driverId}/qualifying")
    suspend fun getDriverQualifyingResult(
        @Path("season") season: String,
        @Path("driverId") driverId: String
    ): DriverQualifyingResultDto

    // Constructor Race result based on season and ID
    @GET("ergast/f1/{season}/constructors/{constructorId}/results")
    suspend fun getConstructorRaceResult(
        @Path("season") season: String,
        @Path("constructorId") constructorId: String
    ): ConstructorRaceResultDto

    // Constructor Qualifying result based on season and ID
    @GET("ergast/f1/{season}/constructors/{constructorId}/qualifying")
    suspend fun getConstructorQualifyingResult(
        @Path("season") season: String,
        @Path("constructorId") constructorId: String
    ): ConstructorQualifyingResultDto

}