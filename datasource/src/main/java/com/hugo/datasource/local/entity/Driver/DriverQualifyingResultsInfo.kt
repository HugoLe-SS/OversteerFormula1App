package com.hugo.datasource.local.entity.Driver

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.RoomDB.TableConstants

@Entity(tableName = TableConstants.DRIVER_QUALIFYING_LIST)
data class DriverQualifyingResultsInfo(
    @PrimaryKey(autoGenerate = false)
    val driverId: String,
    val total: String,
    val driverNumber: String,
    val constructorId: String,
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
    val q3: String?,
)
