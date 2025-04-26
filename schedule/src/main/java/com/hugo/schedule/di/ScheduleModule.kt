package com.hugo.schedule.di

import androidx.lifecycle.ViewModel
import com.hugo.schedule.data.remote.F1ScheduleApi
import com.hugo.schedule.data.repository.F1CalendarRepositoryImpl
import com.hugo.schedule.domain.repository.IF1CalendarRepository
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
class ScheduleModule {

    @Provides
    @Singleton
    fun provideF1StandingsApi(): F1ScheduleApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL_F1_STANDINGS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(F1ScheduleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideF1StandingsRepository(api: F1ScheduleApi): IF1CalendarRepository {
        return F1CalendarRepositoryImpl(api)
    }
}