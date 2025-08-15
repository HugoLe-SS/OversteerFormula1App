package com.hugo.datasource.local.entity.Schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.RoomDB.TableConstants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TableConstants.F1_CIRCUIT_DETAILS)
data class F1CircuitDetails(
    @PrimaryKey(autoGenerate = false)
    val circuitId: String,
    val imageUrl: String?= null,
    val circuitDescription: String?= null,
    val circuitFacts: List<String>?= null,
    val circuitBasicInfo: List<String>?= null,
    val circuitPodiums: List<String>?= null,
    val fastestLaps: List<String>?= null,
    val fastestPit: List<String>?= null,
    val dotd: String?= null
)