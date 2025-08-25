package com.hugo.schedule.di

import com.hugo.datasource.local.RoomDB.LocalDataSource
import com.hugo.schedule.data.remote.F1ScheduleApi
import com.hugo.schedule.data.repository.F1CalendarRepositoryImpl
import com.hugo.schedule.domain.repository.IF1CalendarRepository
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
class ScheduleModule {

    @Provides
    @Singleton
    fun provideF1ScheduleApi(retrofit: Retrofit): F1ScheduleApi =
        retrofit.newBuilder()
            .baseUrl(AppConstants.BASE_URL_F1_STANDINGS)
            .build()
            .create(F1ScheduleApi::class.java)

    @Provides
    @Singleton
    fun provideF1ScheduleRepository(
        api: F1ScheduleApi,
        supabase: SupabaseClient,
        localDataSouce: LocalDataSource
    ): IF1CalendarRepository {
        return F1CalendarRepositoryImpl(api, supabase, localDataSouce)
    }

}