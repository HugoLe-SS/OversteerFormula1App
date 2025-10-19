package com.hugo.datasource.local.RoomDB.datasource.DriverRace

import androidx.paging.PagingSource
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo

interface DriverRaceLocalDataSource {
    //Driver Race
    suspend fun insertDriverRaceListInDB(driverRaceList: List<DriverRaceResultsInfo>)
    fun getDriverRaceResultsPagingSource(driverId: String): PagingSource<Int, DriverRaceResultsInfo>
    suspend fun deleteAllDriverRaceListFromDB(driverId: String)
}