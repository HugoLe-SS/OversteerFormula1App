package com.hugo.datasource.local.entity.Constructor

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.TableConstants

@Entity(tableName = TableConstants.CONSTRUCTOR_RACE_LIST)
data class ConstructorRaceResultsInfo(
    @PrimaryKey(autoGenerate = false)
    val constructorId: String,
    val constructorName: String,
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
    val positionText: String,
    val points: String,
    val grid: String,
    val laps: String,
    val time: String, // 1st place interval
    val fastestLap: String,
    val status: String
)