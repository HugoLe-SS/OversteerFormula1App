package com.hugo.datasource.local.RoomDB.datasource.ConstructorQuali

import com.hugo.datasource.dao.Constructor.ConstructorQualifyingResultsDao
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import javax.inject.Inject

class ConstructorQualifyingImpl @Inject constructor(
    private val constructorQualifyingResultDao: ConstructorQualifyingResultsDao
): ConstructorQualifyingLocalDataSource {

    //Constructor Qualifying
    override fun insertConstructorQualifyingListInDB(constructorQualifyingList: List<ConstructorQualifyingResultsInfo>) {
        constructorQualifyingResultDao.insertConstructorQualifyingResultsInDB(constructorQualifyingList)
    }

    override fun getConstructorQualifyingListFromDB(constructorId: String): List<ConstructorQualifyingResultsInfo> {
        return constructorQualifyingResultDao.getConstructorQualifyingResultsListFromDB(constructorId)
    }

    override fun deleteAllConstructorQualifyingListFromDB() {
        constructorQualifyingResultDao.deleteAllConstructorQualifyingResultsListFromDB()
    }

}