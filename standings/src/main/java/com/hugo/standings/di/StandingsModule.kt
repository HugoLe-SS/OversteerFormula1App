package com.hugo.standings.di

import com.hugo.standings.data.remote.F1StandingsApi
import com.hugo.standings.data.repository.F1StandingRepositoryImpl
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.constants.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class StandingsModule {

    @Provides
    @Singleton
    fun provideF1StandingsApi(): F1StandingsApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL_F1_STANDINGS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(F1StandingsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideF1StandingsRepository(api: F1StandingsApi, supabase: SupabaseClient): IF1StandingsRepository {
        return F1StandingRepositoryImpl(api, supabase)
    }


}