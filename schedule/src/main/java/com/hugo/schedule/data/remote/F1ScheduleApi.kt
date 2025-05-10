package com.hugo.schedule.data.remote

import com.hugo.schedule.data.remote.dto.F1CalendarDto
import com.hugo.schedule.data.remote.dto.F1CalendarResultDto
import retrofit2.http.GET
import retrofit2.http.Path

interface F1ScheduleApi {

    //F1 calendar
    @GET("ergast/f1/{season}")
    suspend fun getF1Calendar(@Path("season") season: String): F1CalendarDto

    //F1 circuit info


    //Result
    @GET("ergast/f1/{season}/circuits/{circuitId}/results")
    suspend fun getF1CalendarResults(
        @Path("season") season: String,
        @Path("circuitId") circuitId: String
    ): F1CalendarResultDto
}