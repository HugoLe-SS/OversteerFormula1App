package com.hugo.datasource.local.entity.Driver

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.TableConstants

@Entity(tableName = TableConstants.DRIVER_RACE_LIST)
data class DriverRaceResultsInfo(
    @PrimaryKey(autoGenerate = false)
    val driverId: String,
    val total: String,
    val driverNumber: String,
    val constructorId: String,
    val constructorName: String,
    val driverCode: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String,
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