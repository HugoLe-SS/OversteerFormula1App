package com.hugo.schedule.di

import com.hugo.schedule.BuildConfig
import com.hugo.schedule.data.remote.F1ScheduleApi
import com.hugo.schedule.data.repository.F1CalendarRepositoryImpl
import com.hugo.schedule.domain.repository.IF1CalendarRepository
import com.hugo.utilities.constants.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ScheduleModule {

    //Provides a singleton instance of the F1ScheduleApi using Retrofit
    @Provides
    @Singleton
    fun provideF1StandingsApi(): F1ScheduleApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL_F1_STANDINGS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(F1ScheduleApi::class.java)
    }

    //Supabase
    @Provides
    @Singleton
    fun provideSupabase(): SupabaseClient {
        val supabase = createSupabaseClient(
            supabaseUrl = AppConstants.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_API_KEY
        ) {
            install(Postgrest)
        }
        return supabase
    }

    @Provides
    @Singleton
    fun provideF1StandingsRepository(api: F1ScheduleApi, supabase: SupabaseClient): IF1CalendarRepository {
        return F1CalendarRepositoryImpl(api, supabase)
    }



}