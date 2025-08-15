package com.hugo.news.data.remote

import com.hugo.news.data.repository.dto.NewsDto
import retrofit2.http.GET

interface NewsApi {
    @GET("apis/site/v2/sports/racing/f1/news")
    suspend fun getF1News(): NewsDto
}