package com.hugo.datasource.local.RoomDB.datasource.F1CalendarResult

import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult

interface F1CalendarResultLocalDataSource {
    fun insertF1CalendarResultInDB(f1CalendarResult: List<F1CalendarRaceResult>)
    fun getF1CalendarResultFromDB(circuitId: String): List<F1CalendarRaceResult>
    fun deleteAllF1CalendarResultFromDB()
}