package com.hugo.datasource.local.entity.Driver

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.RoomDB.TableConstants


@Entity(tableName = TableConstants.DRIVER_STANDINGS_LIST)
data class DriverStandingsInfo (
    @PrimaryKey(autoGenerate = false)
    val driverId: String,
    val constructorId: String,
    val total: Int,
    val season: String,
    val round: String,
    val position: String,
    val points: String,
    val wins: String,
    val driverGivenName: String,
    val driverLastName: String,
    val driverNumber: String,
    val driverCode: String,
    val driverNationality: String,
    val dateOfBirth: String,
    val constructorName: String,
    val constructorNationality: String
)