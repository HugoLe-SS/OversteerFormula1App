package com.hugo.datasource.dao.Constructor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.RoomDB.TableConstants
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo

@Dao
interface ConstructorRaceResultsDao: BaseDao<ConstructorRaceResultsInfo> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConstructorRaceResultsListInDB(constructorRaceResultsList: List<ConstructorRaceResultsInfo>)

    @Query("SELECT * FROM ${TableConstants.CONSTRUCTOR_RACE_LIST} WHERE constructorId = :constructorId")
    fun getConstructorRaceResultsListFromDB(constructorId: String): List<ConstructorRaceResultsInfo>

    @Query("DELETE FROM ${TableConstants.CONSTRUCTOR_RACE_LIST}")
    fun deleteAllConstructorRaceResultsListFromDB()

}