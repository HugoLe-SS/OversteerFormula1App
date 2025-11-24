package com.hugo.datasource.local.RoomDB.datasource.DriverStandings

import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo

interface DriverStandingsLocalDataSource {
    fun insertDriverStandingsListInDB(driverStandingsList: List<DriverStandingsInfo>)
    fun getDriverStandingsListFromDB(): List<DriverStandingsInfo>
    fun deleteAllDriverStandingsListFromDB()
}