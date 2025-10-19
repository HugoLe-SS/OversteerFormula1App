package com.hugo.datasource.local.RoomDB.datasource.DriverDetails

import com.hugo.datasource.local.entity.Driver.DriverDetails

interface DriverDetailsLocalDataSource {
    fun insertDriverDetailsInDB(driverDetails: DriverDetails)
    fun getDriverDetailsFromDB(driverId: String): DriverDetails?
    fun deleteAllDriverDetailsFromDB()
}