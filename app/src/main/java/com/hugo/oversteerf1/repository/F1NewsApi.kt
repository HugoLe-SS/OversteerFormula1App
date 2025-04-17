package com.hugo.oversteerf1.repository

import com.hugo.datasource.local.entity.F1News
import retrofit2.http.GET


interface F1NewsApi {

    @GET("news")
    suspend fun getF1News(): List<F1News>


}