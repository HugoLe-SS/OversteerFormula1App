package com.hugo.datasource.local.RoomDB.datasource.DriverStandings

import com.hugo.datasource.dao.Driver.DriverStandingsDao
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import javax.inject.Inject

class DriverStandingsImpl @Inject constructor(
    private val driverStandingsDao: DriverStandingsDao
): DriverStandingsLocalDataSource {

    //Driver Standings
    override fun insertDriverStandingsListInDB(driverStandingsList: List<DriverStandingsInfo>) {
        driverStandingsDao.insertDriverStandingsListInDB(driverStandingsList)
    }

    override fun getDriverStandingsListFromDB(): List<DriverStandingsInfo> {
        return driverStandingsDao.getDriverStandingsListFromDB()
    }

    override fun deleteAllDriverStandingsListFromDB() {
        driverStandingsDao.deleteAllDriverStandingsListFromDB()
    }
}