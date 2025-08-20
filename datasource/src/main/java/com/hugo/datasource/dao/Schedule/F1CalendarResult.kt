package com.hugo.datasource.dao.Schedule

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.RoomDB.TableConstants
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult

@Dao
interface F1CalendarResult: BaseDao<F1CalendarRaceResult> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertF1CalendarResultInDB(f1CalendarInfo: List<F1CalendarRaceResult>)

    @Query("SELECT * FROM ${TableConstants.F1_CALENDAR_RESULT_LIST} WHERE circuitId = :circuitId")
    fun getF1CalendarResultFromDB(circuitId: String): List<F1CalendarRaceResult>

    @Query("DELETE FROM ${TableConstants.F1_CALENDAR_RESULT_LIST}")
    fun deleteAllF1CalendarResultFromDB()
}