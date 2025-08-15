package com.hugo.datasource.dao.Driver

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.RoomDB.TableConstants
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo

@Dao
interface DriverQualifyingResultsDao: BaseDao<DriverQualifyingResultsInfo> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDriverQualifyingResultsInDB(driverQualifyingResults: List<DriverQualifyingResultsInfo>)

    @Query("SELECT * FROM ${TableConstants.DRIVER_QUALIFYING_LIST} WHERE driverId = :driverId")
    fun getDriverQualifyingResultsListFromDB(driverId: String): List<DriverQualifyingResultsInfo>

    @Query("DELETE FROM ${TableConstants.DRIVER_QUALIFYING_LIST}")
    fun deleteAllDriverQualifyingResultsListFromDB()
}