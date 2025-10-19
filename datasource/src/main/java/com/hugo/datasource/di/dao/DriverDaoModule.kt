package com.hugo.datasource.di.dao


import com.hugo.datasource.dao.Driver.DriverDetailsDao
import com.hugo.datasource.dao.Driver.DriverQualifyingResultsDao
import com.hugo.datasource.dao.Driver.DriverRaceResultsDao
import com.hugo.datasource.dao.Driver.DriverStandingsDao
import com.hugo.datasource.local.RoomDB.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DriverDaoModule {

    @Provides fun provideDriverDetailsDao(db: AppDB): DriverDetailsDao = db.getDriverDetailsDao()
    @Provides fun provideDriverQualifyingResultsDao(db: AppDB): DriverQualifyingResultsDao = db.getDriverQualifyingDao()
    @Provides fun provideDriverRaceResultsDao(db: AppDB): DriverRaceResultsDao = db.getDriverRaceDao()
    @Provides fun provideDriverStandingsDao(db: AppDB): DriverStandingsDao = db.getDriverStandingsDao()

}