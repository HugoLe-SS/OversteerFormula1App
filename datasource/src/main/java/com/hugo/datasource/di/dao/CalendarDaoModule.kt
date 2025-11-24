package com.hugo.datasource.di.dao

import com.hugo.datasource.dao.Schedule.F1CalendarDao
import com.hugo.datasource.dao.Schedule.F1CalendarResultDao
import com.hugo.datasource.dao.Schedule.F1CircuitDetailsDao
import com.hugo.datasource.local.RoomDB.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class CalendarDaoModule {

    @Provides fun provideF1CalendarDao(db: AppDB): F1CalendarDao = db.getF1CalendarDao()
    @Provides fun provideF1CalendarResultDao(db: AppDB): F1CalendarResultDao = db.getF1CalendarResultDao()
    @Provides fun provideF1CircuitDetailsDao(db: AppDB): F1CircuitDetailsDao = db.getF1CircuitDetailsDao()
}