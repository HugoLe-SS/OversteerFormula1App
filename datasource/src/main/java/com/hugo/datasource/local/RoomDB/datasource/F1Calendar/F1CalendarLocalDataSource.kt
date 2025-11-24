package com.hugo.datasource.local.RoomDB.datasource.F1Calendar

import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo

interface F1CalendarLocalDataSource {
    fun insertF1CalendarInDB(f1CalendarInfo: List<F1CalendarInfo>)
    fun getF1CalendarFromDB(): List<F1CalendarInfo>
    fun deleteAllF1CalendarFromDB()
}