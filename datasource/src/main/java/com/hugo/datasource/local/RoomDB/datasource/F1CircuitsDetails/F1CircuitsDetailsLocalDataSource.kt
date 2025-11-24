package com.hugo.datasource.local.RoomDB.datasource.F1CircuitsDetails

import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails

interface F1CircuitsDetailsLocalDataSource {
    fun insertF1CircuitDetailsInDB(f1CircuitDetails: F1CircuitDetails)
    fun getF1CircuitDetailsFromDB(circuitId: String): F1CircuitDetails?
    fun deleteAllF1CircuitDetailsFromDB()
}