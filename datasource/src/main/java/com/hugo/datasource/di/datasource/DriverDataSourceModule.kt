package com.hugo.datasource.di.datasource

import com.hugo.datasource.local.RoomDB.datasource.DriverDetails.DriverDetailsImpl
import com.hugo.datasource.local.RoomDB.datasource.DriverDetails.DriverDetailsLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.DriverQuali.DriverQualifyingImpl
import com.hugo.datasource.local.RoomDB.datasource.DriverQuali.DriverQualifyingLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.DriverRace.DriverRaceImpl
import com.hugo.datasource.local.RoomDB.datasource.DriverRace.DriverRaceLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.DriverStandings.DriverStandingsImpl
import com.hugo.datasource.local.RoomDB.datasource.DriverStandings.DriverStandingsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DriverDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindDriverDetailsLocalDataSource(
        driverDetailsImpl: DriverDetailsImpl
    ): DriverDetailsLocalDataSource

    @Binds
    @Singleton
    abstract fun bindDriverQualifyingLocalDataSource(
        driverQualifyingImpl: DriverQualifyingImpl
    ): DriverQualifyingLocalDataSource

    @Binds
    @Singleton
    abstract fun bindDriverRaceLocalDataSource(
        driverRaceImpl: DriverRaceImpl
    ): DriverRaceLocalDataSource

    @Binds
    @Singleton
    abstract fun bindDriverStandingsLocalDataSource(
        driverStandingsImpl: DriverStandingsImpl
    ): DriverStandingsLocalDataSource

}