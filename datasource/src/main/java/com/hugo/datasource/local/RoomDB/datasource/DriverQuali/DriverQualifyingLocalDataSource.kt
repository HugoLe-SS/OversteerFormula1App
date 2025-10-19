package com.hugo.datasource.local.RoomDB.datasource.DriverQuali

import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo

interface DriverQualifyingLocalDataSource {
    fun insertDriverQualifyingListInDB(driverQualifyingList: List<DriverQualifyingResultsInfo>)
    fun getDriverQualifyingListFromDB(driverId: String): List<DriverQualifyingResultsInfo>
    fun deleteAllDriverQualifyingListFromDB()
}