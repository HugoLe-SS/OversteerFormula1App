package com.hugo.datasource.di.dao

import com.hugo.datasource.dao.Home.F1HomeDetailsDao
import com.hugo.datasource.local.RoomDB.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class HomeDaoModule {
    @Provides fun provideF1HomeDetailsDao(db: AppDB): F1HomeDetailsDao = db.getF1HomeDetailsDao()
}