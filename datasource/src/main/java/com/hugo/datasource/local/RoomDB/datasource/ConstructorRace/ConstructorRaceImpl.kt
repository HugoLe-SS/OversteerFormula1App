package com.hugo.datasource.local.RoomDB.datasource.ConstructorRace

import androidx.paging.PagingSource
import com.hugo.datasource.dao.Constructor.ConstructorRaceResultsDao
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import javax.inject.Inject

class ConstructorRaceImpl @Inject constructor(
    private val constructorRaceResultsDao: ConstructorRaceResultsDao
): ConstructorRaceLocalDataSource {

    //Constructor race results
    override suspend fun insertConstructorRaceListInDB(constructorRaceList: List<ConstructorRaceResultsInfo>) {
        constructorRaceResultsDao.insertConstructorRaceResultsListInDB(constructorRaceList)
    }

    override fun getConstructorRacePagingSource(constructorId: String): PagingSource<Int, ConstructorRaceResultsInfo> {
        return constructorRaceResultsDao.getConstructorRaceResultsPagingSource(constructorId)
    }

    override suspend fun deleteAllConstructorRaceListFromDB(constructorId: String) {
        constructorRaceResultsDao.deleteAllConstructorRaceResultsListFromDB(constructorId)
    }
}