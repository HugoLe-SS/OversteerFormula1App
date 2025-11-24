package com.hugo.datasource.local.RoomDB.datasource.F1CircuitsDetails

import com.hugo.datasource.dao.Schedule.F1CircuitDetailsDao
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import javax.inject.Inject

class F1CircuitsDetailsImpl @Inject constructor(
    private  val f1CircuitsDetailsDao: F1CircuitDetailsDao
): F1CircuitsDetailsLocalDataSource {


    override fun insertF1CircuitDetailsInDB(f1CircuitDetails: F1CircuitDetails) {
        f1CircuitsDetailsDao.insertF1CircuitDetailsInDB(f1CircuitDetails)
    }

    override fun getF1CircuitDetailsFromDB(circuitId: String): F1CircuitDetails? {
        return f1CircuitsDetailsDao.getF1CircuitDetailsFromDB(circuitId)
    }

    override fun deleteAllF1CircuitDetailsFromDB() {
        f1CircuitsDetailsDao.deleteAllF1CircuitDetailsFromDB()
    }
}