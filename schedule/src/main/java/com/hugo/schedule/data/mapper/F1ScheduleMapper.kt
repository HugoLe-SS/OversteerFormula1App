package com.hugo.schedule.data.mapper

import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.SessionInfo
import com.hugo.schedule.data.remote.dto.F1CalendarDto
import com.hugo.schedule.data.remote.dto.SessionInfoDto

fun SessionInfoDto.toDomain() = SessionInfo(date, time)

fun F1CalendarDto.toF1CalendarInfoList(): List<F1CalendarInfo> {
    val total = mrData.total

    return mrData.raceTable.races.map { schedule ->
        F1CalendarInfo(
            total = total,
            season = schedule.season,
            round = schedule.round,
            circuit = schedule.circuit.circuitName,
            circuitId = schedule.circuit.circuitId,
            raceName = schedule.raceName,
            locality = schedule.circuit.location.locality,
            country = schedule.circuit.location.country,
            mainRaceDate = schedule.date,
            mainRaceTime = schedule.time ?: "",
            firstPractice = schedule.firstPractice?.toDomain(),
            secondPractice = schedule.secondPractice?.toDomain(),
            thirdPractice = schedule.thirdPractice?.toDomain(),
            qualifying = schedule.qualifying?.toDomain(),
            sprintQualifying = schedule.sprintQualifying?.toDomain(),
            sprintRace = schedule.sprint?.toDomain()
        )
    }
}

