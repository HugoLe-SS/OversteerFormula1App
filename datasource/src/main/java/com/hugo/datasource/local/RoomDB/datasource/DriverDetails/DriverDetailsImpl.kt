package com.hugo.datasource.local.RoomDB.datasource.DriverDetails

import com.hugo.datasource.dao.Driver.DriverDetailsDao
import com.hugo.datasource.local.entity.Driver.DriverDetails
import javax.inject.Inject

class DriverDetailsImpl @Inject constructor(
    private val driverDetailsDao: DriverDetailsDao
): DriverDetailsLocalDataSource {

    override fun insertDriverDetailsInDB(driverDetails: DriverDetails) {
        driverDetailsDao.insertDriverDetailsInDB(driverDetails)
    }

    override fun getDriverDetailsFromDB(driverId: String): DriverDetails? {
        return driverDetailsDao.getDriverDetailsFromDB(driverId)
    }

    override fun deleteAllDriverDetailsFromDB() {
        driverDetailsDao.deleteAllDriverDetailsFromDB()
    }

}