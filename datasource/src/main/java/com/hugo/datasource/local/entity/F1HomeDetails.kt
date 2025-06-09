package com.hugo.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.TableConstants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TableConstants.F1_HOME_DETAILS)
data class F1HomeDetails(
    @PrimaryKey(autoGenerate = false)
    val circuitId: String,
    val raceName: String?= null,
    val raceDate: String?= null,
    val raceTime: String?= null,
    val topDriversStandings: List<String>?= null,
    val topConstructorsStandings: List<String>?= null,
    val lastRaceName: String?= null,
    val lastRacePodiums: List<String>?= null

)
