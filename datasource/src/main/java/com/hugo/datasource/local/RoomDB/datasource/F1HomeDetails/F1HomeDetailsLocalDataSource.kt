package com.hugo.datasource.local.RoomDB.datasource.F1HomeDetails

import com.hugo.datasource.local.entity.F1HomeDetails

interface F1HomeDetailsLocalDataSource {
    fun insertF1HomeDetailsInDB(f1HomeDetails: List<F1HomeDetails>)
    fun getF1HomeDetailsFromDB(): List<F1HomeDetails>?
    fun deleteAllF1HomeDetailsFromDB()
}
