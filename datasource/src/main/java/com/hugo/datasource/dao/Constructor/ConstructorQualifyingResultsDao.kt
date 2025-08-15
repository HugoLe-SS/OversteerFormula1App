package com.hugo.datasource.dao.Constructor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.RoomDB.TableConstants
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo

@Dao
interface ConstructorQualifyingResultsDao: BaseDao<ConstructorQualifyingResultsInfo> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConstructorQualifyingResultsInDB(constructorQualifyingResult: List<ConstructorQualifyingResultsInfo>)

    @Query("SELECT * FROM ${TableConstants.CONSTRUCTOR_QUALIFYING_LIST} WHERE constructorId = :constructorId")
    fun getConstructorQualifyingResultsListFromDB(constructorId: String): List<ConstructorQualifyingResultsInfo>

    @Query("DELETE FROM ${TableConstants.CONSTRUCTOR_QUALIFYING_LIST}")
    fun deleteAllConstructorQualifyingResultsListFromDB()

}