package com.hugo.datasource.di.datasource


import com.hugo.datasource.local.RoomDB.datasource.F1Calendar.F1CalendarImpl
import com.hugo.datasource.local.RoomDB.datasource.F1Calendar.F1CalendarLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.F1CalendarResult.F1CalendarResultImpl
import com.hugo.datasource.local.RoomDB.datasource.F1CalendarResult.F1CalendarResultLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CalendarDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindF1CalendarLocalDataSource(
        f1CalendarImpl: F1CalendarImpl
    ): F1CalendarLocalDataSource

    @Binds
    @Singleton
    abstract fun bindF1CalendarResultLocalDataSource(
        f1CalendarResultImpl: F1CalendarResultImpl
    ): F1CalendarResultLocalDataSource

}