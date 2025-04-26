package com.hugo.schedule.data.mapper

import com.hugo.schedule.data.remote.dto.F1CalendarDto
import com.hugo.schedule.data.remote.dto.SessionInfoDto
import com.hugo.schedule.domain.model.F1CalendarInfo
import com.hugo.schedule.domain.model.SessionInfo

fun SessionInfoDto.toDomain() = SessionInfo(date, time)

fun F1CalendarDto.toF1CalendarInfoList(): List<F1CalendarInfo> {
    val total = mrData.total

    return mrData.raceTable.races.map { schedule ->
        F1CalendarInfo(
            total = total,
            season = schedule.season,
            round = schedule.round,
            circuit = schedule.circuit.circuitName,
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

//fun SessionInfoDto.toDomain(): SessionInfo? {
//    return if (date.isNotEmpty()) {
//        SessionInfo(
//            date = date,
//            time = time
//        )
//    } else {
//        null
//    }
//}