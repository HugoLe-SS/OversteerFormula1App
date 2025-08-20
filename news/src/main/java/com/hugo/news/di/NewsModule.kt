package com.hugo.news.di

import com.hugo.news.data.remote.NewsApi
import com.hugo.news.data.repository.NewsRepositoryImpl
import com.hugo.news.domain.repository.INewsRepository
import com.hugo.utilities.constants.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {
    @Provides
    @Singleton
    fun provideF1StandingsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL_F1_NEWS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideF1StandingsRepository(api: NewsApi): INewsRepository {
        return NewsRepositoryImpl(api)
    }

}