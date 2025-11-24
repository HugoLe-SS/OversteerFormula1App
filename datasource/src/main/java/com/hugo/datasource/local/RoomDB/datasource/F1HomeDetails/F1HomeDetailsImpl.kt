package com.hugo.datasource.local.RoomDB.datasource.F1HomeDetails

import com.hugo.datasource.dao.Home.F1HomeDetailsDao
import com.hugo.datasource.local.entity.F1HomeDetails
import javax.inject.Inject

class F1HomeDetailsImpl @Inject constructor(
    private val f1HomeDetailsDao: F1HomeDetailsDao
): F1HomeDetailsLocalDataSource {

    override fun insertF1HomeDetailsInDB(f1HomeDetails: List<F1HomeDetails>) {
        f1HomeDetailsDao.insertF1HomeDetailsInDB(f1HomeDetails)
    }

    override fun getF1HomeDetailsFromDB(): List<F1HomeDetails>? {
        return f1HomeDetailsDao.getF1HomeDetailsFromDB()
    }

    override fun deleteAllF1HomeDetailsFromDB() {
        f1HomeDetailsDao.deleteAllF1HomeDetailsFromDB()
    }

}