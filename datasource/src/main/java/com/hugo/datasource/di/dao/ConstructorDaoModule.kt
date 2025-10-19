package com.hugo.datasource.di.dao

import com.hugo.datasource.dao.Constructor.ConstructorDetailsDao
import com.hugo.datasource.dao.Constructor.ConstructorQualifyingResultsDao
import com.hugo.datasource.dao.Constructor.ConstructorRaceResultsDao
import com.hugo.datasource.dao.Constructor.ConstructorStandingsDao
import com.hugo.datasource.local.RoomDB.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ConstructorDaoModule {

    @Provides fun provideConstructorDetailsDao(db: AppDB): ConstructorDetailsDao = db.getConstructorDetailsDao()
    @Provides fun provideConstructorQualifyingResultsDao(db: AppDB): ConstructorQualifyingResultsDao = db.getConstructorQualifyingDao()
    @Provides fun provideConstructorRaceResultsDao(db: AppDB): ConstructorRaceResultsDao = db.getConstructorRaceDao()
    @Provides fun provideConstructorStandingsDao(db: AppDB): ConstructorStandingsDao = db.getConstructorStandingsDao()

}