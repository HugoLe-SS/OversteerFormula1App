package com.hugo.datasource.di.datasource

import com.hugo.datasource.local.RoomDB.datasource.F1CircuitsDetails.F1CircuitsDetailsImpl
import com.hugo.datasource.local.RoomDB.datasource.F1CircuitsDetails.F1CircuitsDetailsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CircuitDetailsDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindF1CircuitDetailsLocalDataSource(
        f1CircuitsDetailsImpl: F1CircuitsDetailsImpl
    ): F1CircuitsDetailsLocalDataSource
}