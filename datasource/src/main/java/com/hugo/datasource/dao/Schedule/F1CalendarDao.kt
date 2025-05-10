package com.hugo.datasource.dao.Schedule

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.TableConstants
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo

@Dao
interface F1CalendarDao: BaseDao<F1CalendarInfo> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertF1CalendarInDB(f1CalendarInfo: List<F1CalendarInfo>)

    @Query("SELECT * FROM ${TableConstants.F1_CALENDAR_INFO_LIST}")
    fun getF1CalendarFromDB(): List<F1CalendarInfo>

    @Query("DELETE FROM ${TableConstants.F1_CALENDAR_INFO_LIST}")
    fun deleteAllF1CalendarFromDB()
}