package com.hugo.datasource.local.RoomDB.datasource.DriverRace

import androidx.paging.PagingSource
import com.hugo.datasource.dao.Driver.DriverRaceResultsDao
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import javax.inject.Inject

class DriverRaceImpl @Inject constructor(
    private val driverRaceResultsDao: DriverRaceResultsDao
): DriverRaceLocalDataSource {

    override suspend fun insertDriverRaceListInDB(driverRaceList: List<DriverRaceResultsInfo>) {
        driverRaceResultsDao.insertDriverRaceResultsListInDB(driverRaceList)
    }

    override fun getDriverRaceResultsPagingSource(driverId: String): PagingSource<Int, DriverRaceResultsInfo> {
        return driverRaceResultsDao.getDriverRaceResultsPagingSource(driverId)
    }

    override suspend fun deleteAllDriverRaceListFromDB(driverId: String) {
        driverRaceResultsDao.deleteAllDriverRaceResultsListFromDB(driverId)
    }
}