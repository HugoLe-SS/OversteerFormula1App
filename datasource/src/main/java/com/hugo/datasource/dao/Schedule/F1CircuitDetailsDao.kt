package com.hugo.datasource.dao.Schedule

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.RoomDB.TableConstants
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails

@Dao
interface F1CircuitDetailsDao: BaseDao<F1CircuitDetails> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertF1CircuitDetailsInDB(f1CalendarInfo: F1CircuitDetails)

    @Query("SELECT * FROM ${TableConstants.F1_CIRCUIT_DETAILS} WHERE circuitId = :circuitId")
    fun getF1CircuitDetailsFromDB(circuitId: String): F1CircuitDetails?

    @Query("DELETE FROM ${TableConstants.F1_CIRCUIT_DETAILS}")
    fun deleteAllF1CircuitDetailsFromDB()
}