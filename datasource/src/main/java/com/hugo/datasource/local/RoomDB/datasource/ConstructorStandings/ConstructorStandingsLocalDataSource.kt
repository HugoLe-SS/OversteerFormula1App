package com.hugo.datasource.local.RoomDB.datasource.ConstructorStandings

import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo

interface ConstructorStandingsLocalDataSource {
    fun insertConstructorStandingsListInDB(constructorStandingsList: List<ConstructorStandingsInfo>)
    fun getConstructorStandingsListFromDB(): List<ConstructorStandingsInfo>
    fun deleteAllConstructorStandingsListFromDB()
}