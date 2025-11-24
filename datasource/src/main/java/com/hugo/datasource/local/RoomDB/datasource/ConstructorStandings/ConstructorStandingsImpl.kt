package com.hugo.datasource.local.RoomDB.datasource.ConstructorStandings

import com.hugo.datasource.dao.Constructor.ConstructorStandingsDao
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import javax.inject.Inject

class ConstructorStandingsImpl @Inject constructor(
    private val constructorStandingsDao: ConstructorStandingsDao
): ConstructorStandingsLocalDataSource {

    //Constructor Standings
    override fun insertConstructorStandingsListInDB(constructorStandingsList: List<ConstructorStandingsInfo>) {
        constructorStandingsDao.insertConstructorStandingsListInDB(constructorStandingsList)
    }

    override fun getConstructorStandingsListFromDB(): List<ConstructorStandingsInfo> {
        return constructorStandingsDao.getConstructorStandingsListFromDB()
    }

    override fun deleteAllConstructorStandingsListFromDB() {
        constructorStandingsDao.deleteAllConstructorStandingsListFromDB()
    }

}