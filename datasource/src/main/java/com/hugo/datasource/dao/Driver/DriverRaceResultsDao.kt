package com.hugo.datasource.dao.Driver

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.RoomDB.TableConstants
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
@Dao
interface DriverRaceResultsDao : BaseDao<DriverRaceResultsInfo> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriverRaceResultsListInDB(driverRaceResults: List<DriverRaceResultsInfo>)

    @Query("SELECT * FROM ${TableConstants.DRIVER_RACE_LIST} WHERE driverId = :driverId ORDER BY round ASC")
    fun getDriverRaceResultsPagingSource(driverId: String): PagingSource<Int, DriverRaceResultsInfo>

    @Query("DELETE FROM ${TableConstants.DRIVER_RACE_LIST} WHERE driverId = :driverId")
    suspend fun deleteAllDriverRaceResultsListFromDB(driverId: String)
}