package com.hugo.schedule.data.remote

import com.hugo.schedule.data.remote.dto.F1CalendarDto
import retrofit2.http.GET
import retrofit2.http.Path

interface F1ScheduleApi {

    //F1 calendar
    @GET("ergast/f1/{season}")
    suspend fun getF1Calendar(@Path("season") season: String): F1CalendarDto
}