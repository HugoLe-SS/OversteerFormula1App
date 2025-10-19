package com.hugo.datasource.local.RoomDB.datasource.ConstructorDetails

import com.hugo.datasource.dao.Constructor.ConstructorDetailsDao
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import javax.inject.Inject

class ConstructorDetailsImpl @Inject constructor(
    private val constructorDetailsDao: ConstructorDetailsDao,
): ConstructorDetailsLocalDataSource {

    override fun insertConstructorDetailsInDB(constructorDetails: ConstructorDetails) {
        constructorDetailsDao.insertConstructorDetailsInDB(constructorDetails)
    }

    override fun getConstructorDetailsFromDB(constructorId: String): ConstructorDetails? {
        return constructorDetailsDao.getConstructorDetailsFromDB(constructorId)
    }

    override fun deleteAllConstructorDetailsFromDB() {
        constructorDetailsDao.deleteAllConstructorDetailsFromDB()
    }
}