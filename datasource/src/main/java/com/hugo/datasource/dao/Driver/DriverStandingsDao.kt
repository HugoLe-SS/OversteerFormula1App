package com.hugo.datasource.dao.Driver

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.TableConstants
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo

@Dao
interface DriverStandingsDao : BaseDao<DriverStandingsInfo> {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertDriverStandingsListInDB(driverStandingsList: List<DriverStandingsInfo>)

        @Query("SELECT * FROM ${TableConstants.DRIVER_STANDINGS_LIST}")
        fun getDriverStandingsListFromDB(): List<DriverStandingsInfo>

        @Query("DELETE FROM ${TableConstants.DRIVER_STANDINGS_LIST}")
        fun deleteAllDriverStandingsListFromDB()
}