package com.hugo.datasource.dao.Driver

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.TableConstants
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo

@Dao
interface DriverRaceResultsDao: BaseDao<DriverRaceResultsInfo> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDriverRaceResultsListInDB(driverRaceResults: List<DriverRaceResultsInfo>)

    @Query("SELECT * FROM ${TableConstants.DRIVER_RACE_LIST} WHERE driverId = :driverId")
    fun getDriverRaceResultsListFromDB(driverId: String): List<DriverRaceResultsInfo>

    @Query("DELETE FROM ${TableConstants.DRIVER_RACE_LIST}")
    fun deleteAllDriverRaceResultsListFromDB()

}