package com.hugo.datasource.local.RoomDB.datasource.ConstructorRace

import androidx.paging.PagingSource
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo

interface ConstructorRaceLocalDataSource {
    suspend fun insertConstructorRaceListInDB(constructorRaceList: List<ConstructorRaceResultsInfo>)
    fun getConstructorRacePagingSource(constructorId: String): PagingSource<Int, ConstructorRaceResultsInfo>
    suspend fun deleteAllConstructorRaceListFromDB(constructorId: String)
}