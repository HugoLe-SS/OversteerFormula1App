package com.hugo.datasource.di.datasource

import com.hugo.datasource.local.RoomDB.datasource.F1HomeDetails.F1HomeDetailsImpl
import com.hugo.datasource.local.RoomDB.datasource.F1HomeDetails.F1HomeDetailsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class F1HomeDetailsDataSourceModule{


    @Binds
    @Singleton
    abstract fun bindF1HomeDetailsLocalDataSource(
        impl: F1HomeDetailsImpl
    ): F1HomeDetailsLocalDataSource
}