package com.hugo.datasource.local.RoomDB.datasource.ConstructorQuali

import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo

interface ConstructorQualifyingLocalDataSource {
    fun insertConstructorQualifyingListInDB(constructorQualifyingList: List<ConstructorQualifyingResultsInfo>)
    fun getConstructorQualifyingListFromDB(constructorId: String): List<ConstructorQualifyingResultsInfo>
    fun deleteAllConstructorQualifyingListFromDB()
}