package com.hugo.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.RoomDB.TableConstants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TableConstants.F1_HOME_DETAILS)
data class F1HomeDetails(
    @PrimaryKey(autoGenerate = false)
    val circuitId: String,
    val raceName: String?= null,
    val raceDate: String?= null,
    val raceTime: String?= null,
    val lastRaceName: String?= null,
    val lastRacePodiums: List<String>?= null,
    val driverImg: String?= null,
    val raceImg: String?= null,
    val driverStandingsImg: String?= null,
    val constructorStandingsImg: String?= null,

)
