package com.hugo.datasource.dao.Home

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.RoomDB.TableConstants
import com.hugo.datasource.local.entity.F1HomeDetails

@Dao
interface F1HomeDetailsDao: BaseDao<F1HomeDetails> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertF1HomeDetailsInDB(homeDetails: List<F1HomeDetails>)

    @Query("SELECT * FROM ${TableConstants.F1_HOME_DETAILS}")
    fun getF1HomeDetailsFromDB(): List<F1HomeDetails>?

    @Query("DELETE FROM ${TableConstants.F1_HOME_DETAILS}")
    fun deleteAllF1HomeDetailsFromDB()
}