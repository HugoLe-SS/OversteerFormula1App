package com.hugo.datasource.local.entity.Schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.TableConstants
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = TableConstants.F1_CALENDAR_INFO_LIST)
data class F1CalendarInfo(
    @PrimaryKey(autoGenerate = false)
    val circuitId: String,
    val total: Int,
    val season: String,
    val round: String,
    val circuit: String,
    val raceName: String,
    val locality: String,
    val country: String,
    val mainRaceDate: String,
    val mainRaceTime: String,
    val firstPractice: SessionInfo?,
    val secondPractice: SessionInfo?,
    val thirdPractice: SessionInfo?,
    val qualifying: SessionInfo?,
    val sprintQualifying: SessionInfo?,
    val sprintRace: SessionInfo?
//    @Embedded(prefix = "fp_")
//    val firstPractice: SessionInfo?,
//    @Embedded(prefix = "sp_")
//    val secondPractice: SessionInfo?,
//    @Embedded(prefix = "tp_")
//    val thirdPractice: SessionInfo?,
//    @Embedded(prefix = "q_")
//    val qualifying: SessionInfo?,
//    @Embedded(prefix = "sq_")
//    val sprintQualifying: SessionInfo?,
//    @Embedded(prefix = "sr_")
//    val sprintRace: SessionInfo?
)

@Serializable
data class SessionInfo(
    val date: String,
    val time: String?
)