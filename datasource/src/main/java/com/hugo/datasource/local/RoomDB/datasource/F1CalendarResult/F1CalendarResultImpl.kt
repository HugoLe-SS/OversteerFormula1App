package com.hugo.datasource.local.RoomDB.datasource.F1CalendarResult

import com.hugo.datasource.dao.Schedule.F1CalendarResultDao
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import javax.inject.Inject

class F1CalendarResultImpl @Inject constructor(
    private val f1CalendarResultDao: F1CalendarResultDao
): F1CalendarResultLocalDataSource{

    // Calendar Result
    override fun insertF1CalendarResultInDB(f1CalendarResult: List<F1CalendarRaceResult>) {
        f1CalendarResultDao.insertF1CalendarResultInDB(f1CalendarResult)
    }

    override fun getF1CalendarResultFromDB(circuitId: String): List<F1CalendarRaceResult> {
        return f1CalendarResultDao.getF1CalendarResultFromDB(circuitId)
    }

    override fun deleteAllF1CalendarResultFromDB() {
        f1CalendarResultDao.deleteAllF1CalendarResultFromDB()
    }
}