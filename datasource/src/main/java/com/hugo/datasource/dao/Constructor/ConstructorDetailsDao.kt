package com.hugo.datasource.dao.Constructor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.RoomDB.TableConstants
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails

@Dao
interface ConstructorDetailsDao: BaseDao<ConstructorDetails> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConstructorDetailsInDB(constructorDetails: ConstructorDetails)

    @Query("SELECT * FROM ${TableConstants.CONSTRUCTOR_DETAILS} WHERE constructorId = :constructorId")
    fun getConstructorDetailsFromDB(constructorId: String): ConstructorDetails?


    @Query("DELETE FROM ${TableConstants.CONSTRUCTOR_DETAILS}")
    fun deleteAllConstructorDetailsFromDB()
}