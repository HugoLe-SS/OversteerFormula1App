package com.hugo.datasource.local.entity.Constructor

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.RoomDB.TableConstants

@Entity(tableName = TableConstants.CONSTRUCTOR_QUALIFYING_LIST)
data class ConstructorQualifyingResultsInfo(
   @PrimaryKey(autoGenerate = false)
    val constructorId: String,
    val total: String,
    val driverNumber: String,
    val driverId: String,
    val driverCode: String,
    val givenName: String,
    val familyName: String,
    val season: String,
    val round: String,
    val raceName: String,
    val circuitId: String,
    val circuitName: String,
    val country: String,
    val position: String,
    val q1: String?,
    val q2: String?,
    val q3:String?,
)