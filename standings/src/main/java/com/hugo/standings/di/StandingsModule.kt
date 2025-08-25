package com.hugo.standings.di

import com.hugo.datasource.local.RoomDB.LocalDataSource
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
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class StandingsModule {

    @Provides
    @Singleton
    fun provideF1StandingsApi(retrofit: Retrofit): F1StandingsApi =
    retrofit.newBuilder()
        .baseUrl(AppConstants.BASE_URL_F1_STANDINGS)
        .build()
        .create(F1StandingsApi::class.java)


    @Provides
    @Singleton
    fun provideF1StandingsRepository(
        api: F1StandingsApi,
        supabase: SupabaseClient,
        localDataSource: LocalDataSource
    ): IF1StandingsRepository {
        return F1StandingRepositoryImpl(api, supabase, localDataSource)
    }


}