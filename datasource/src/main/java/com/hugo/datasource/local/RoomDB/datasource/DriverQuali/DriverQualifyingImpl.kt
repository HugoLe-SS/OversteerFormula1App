package com.hugo.datasource.local.RoomDB.datasource.DriverQuali

import com.hugo.datasource.dao.Driver.DriverQualifyingResultsDao
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import javax.inject.Inject

class DriverQualifyingImpl @Inject constructor(
    private val driverQualifyingResultsDao: DriverQualifyingResultsDao
): DriverQualifyingLocalDataSource {

    //Driver Qualifying
    override fun insertDriverQualifyingListInDB(driverQualifyingList: List<DriverQualifyingResultsInfo>) {
        driverQualifyingResultsDao.insertDriverQualifyingResultsInDB(driverQualifyingList)
    }

    override fun getDriverQualifyingListFromDB(driverId: String): List<DriverQualifyingResultsInfo> {
        return driverQualifyingResultsDao.getDriverQualifyingResultsListFromDB(driverId)
    }

    override fun deleteAllDriverQualifyingListFromDB() {
        driverQualifyingResultsDao.deleteAllDriverQualifyingResultsListFromDB()
    }
}