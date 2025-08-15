package com.hugo.datasource.dao.Driver

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.RoomDB.TableConstants
import com.hugo.datasource.local.entity.Driver.DriverDetails

@Dao
interface DriverDetailsDao: BaseDao<DriverDetails> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDriverDetailsInDB(driverDetails: DriverDetails)

    @Query("SELECT * FROM ${TableConstants.DRIVER_DETAILS} WHERE driverId = :driverId")
    fun getDriverDetailsFromDB(driverId: String): DriverDetails?

    @Query("DELETE FROM ${TableConstants.DRIVER_DETAILS}")
    fun deleteAllDriverDetailsFromDB()

}