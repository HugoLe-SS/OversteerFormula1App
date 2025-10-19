package com.hugo.datasource.local.RoomDB.datasource.ConstructorDetails

import com.hugo.datasource.local.entity.Constructor.ConstructorDetails

interface ConstructorDetailsLocalDataSource {
    fun insertConstructorDetailsInDB(constructorDetails: ConstructorDetails)
    fun getConstructorDetailsFromDB(constructorId: String): ConstructorDetails?
    fun deleteAllConstructorDetailsFromDB()
}