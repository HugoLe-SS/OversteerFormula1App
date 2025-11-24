package com.hugo.datasource.local.RoomDB.datasource.F1Calendar

import com.hugo.datasource.dao.Schedule.F1CalendarDao
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import javax.inject.Inject

class F1CalendarImpl @Inject constructor(
    private val f1CalendarDao: F1CalendarDao
): F1CalendarLocalDataSource {

    override fun insertF1CalendarInDB(f1CalendarInfo: List<F1CalendarInfo>) {
        f1CalendarDao.insertF1CalendarInDB(f1CalendarInfo)
    }

    override fun getF1CalendarFromDB(): List<F1CalendarInfo> {
        return f1CalendarDao.getF1CalendarFromDB()
    }

    override fun deleteAllF1CalendarFromDB() {
        f1CalendarDao.deleteAllF1CalendarFromDB()
    }
}