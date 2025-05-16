package com.hugo.datasource.dao.Constructor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.datasource.dao.BaseDao
import com.hugo.datasource.local.TableConstants
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo

@Dao
interface ConstructorStandingsDao: BaseDao<ConstructorStandingsInfo> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConstructorStandingsListInDB(constructorStandingsList: List<ConstructorStandingsInfo>)

    @Query("SELECT * FROM ${TableConstants.CONSTRUCTOR_STANDINGS_LIST}")
    fun getConstructorStandingsListFromDB(): List<ConstructorStandingsInfo>

    @Query("DELETE FROM ${TableConstants.CONSTRUCTOR_STANDINGS_LIST}")
    fun deleteAllConstructorStandingsListFromDB()

}

