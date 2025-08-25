package com.hugo.result.di

import com.hugo.datasource.local.RoomDB.LocalDataSource
import com.hugo.result.data.remote.F1ResultApi
import com.hugo.result.data.repository.F1ResultRepositoryImpl
import com.hugo.result.domain.repository.IF1ResultRepository
import com.hugo.utilities.constants.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ResultModule {

    @Provides
    @Singleton
    fun provideF1ResultApi(retrofit: Retrofit): F1ResultApi =
        retrofit.newBuilder()
            .baseUrl(AppConstants.BASE_URL_F1_STANDINGS)
            .build()
            .create(F1ResultApi::class.java)

    @Provides
    @Singleton
    fun provideF1ResultRepository(api: F1ResultApi, localDataSource: LocalDataSource): IF1ResultRepository {
        return F1ResultRepositoryImpl(api, localDataSource)
    }

}