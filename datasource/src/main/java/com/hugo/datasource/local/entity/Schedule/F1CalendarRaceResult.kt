package com.hugo.datasource.local.entity.Schedule

import androidx.room.Entity
import com.hugo.datasource.local.RoomDB.TableConstants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TableConstants.F1_CALENDAR_RESULT_LIST,
        primaryKeys = ["circuitId", "position"]
)
data class F1CalendarRaceResult(
    //@PrimaryKey(autoGenerate = false)
    val circuitId: String,
    val constructorId: String,
    val total: String,
    val driverNumber: String,
    val driverId: String,
    val constructorName: String,
    val driverCode: String,
    val givenName: String,
    val familyName: String,
    val season: String,
    val round: String,
    val raceName: String,
    val circuitName: String,
    val country: String,
    val position: Int,
    val positionText: String,
    val points: String,
    val grid: String,
    val laps: String,
    val time: String, // 1st place interval
    val millis: String?, // Milliseconds of the 1st place interval
    val fastestLap: String,
    val status: String,


    val interval: String? = null // This is computed value, does not exist in the database
)