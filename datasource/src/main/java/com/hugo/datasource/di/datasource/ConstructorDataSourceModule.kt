package com.hugo.datasource.di.datasource

import com.hugo.datasource.local.RoomDB.datasource.ConstructorDetails.ConstructorDetailsImpl
import com.hugo.datasource.local.RoomDB.datasource.ConstructorDetails.ConstructorDetailsLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.ConstructorQuali.ConstructorQualifyingImpl
import com.hugo.datasource.local.RoomDB.datasource.ConstructorQuali.ConstructorQualifyingLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.ConstructorRace.ConstructorRaceImpl
import com.hugo.datasource.local.RoomDB.datasource.ConstructorRace.ConstructorRaceLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.ConstructorStandings.ConstructorStandingsImpl
import com.hugo.datasource.local.RoomDB.datasource.ConstructorStandings.ConstructorStandingsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ConstructorDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindConstructorDetailsLocalDataSource(
        constructorDetailsImpl: ConstructorDetailsImpl
    ): ConstructorDetailsLocalDataSource

    @Binds
    @Singleton
    abstract fun bindConstructorQualifyingLocalDataSource(
        constructorQualifyingImpl: ConstructorQualifyingImpl
    ): ConstructorQualifyingLocalDataSource

    @Binds
    @Singleton
    abstract fun bindConstructorRaceLocalDataSource(
        constructorRaceImpl: ConstructorRaceImpl
    ): ConstructorRaceLocalDataSource

    @Binds
    @Singleton
    abstract fun bindConstructorStandingsLocalDataSource(
        constructorStandingsImpl: ConstructorStandingsImpl
    ): ConstructorStandingsLocalDataSource

}