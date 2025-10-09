package com.hugo.datasource.di

import android.content.Context
import androidx.room.Room
import com.hugo.datasource.local.RoomDB.AppDB
import com.hugo.datasource.local.RoomDB.LocalDataSource
import com.hugo.datasource.local.RoomDB.LocalDataSourceImpl
import com.hugo.datasource.local.TransactionProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(@ApplicationContext context: Context): LocalDataSource {
        return LocalDataSourceImpl(context)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDB {
        return Room.databaseBuilder(
            context,
            AppDB::class.java,
            "app_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideTransactionProvider(db: AppDB): TransactionProvider {
        return TransactionProvider(db)
    }

}