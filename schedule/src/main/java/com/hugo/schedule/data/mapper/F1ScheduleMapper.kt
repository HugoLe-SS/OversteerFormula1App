package com.hugo.schedule.data.mapper

import com.hugo.schedule.data.remote.dto.F1CalendarDto
import com.hugo.schedule.data.remote.dto.F1CalendarResultDto
import com.hugo.schedule.data.remote.dto.SessionInfoDto
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.datasource.local.entity.Schedule.SessionInfo

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

fun F1CalendarResultDto.toF1CalendarResultList(): List<F1CalendarRaceResult> {
    val total = mrData.total.toString()

    return mrData.raceTable.races.flatMap { race ->
        race.results.map { result ->
            F1CalendarRaceResult(
                total = total,
                driverNumber = result.number,
                driverId = result.driver.driverId,
                constructorId = result.constructor.constructorId,
                constructorName = result.constructor.name,
                driverCode = result.driver.code,
                givenName = result.driver.givenName,
                familyName = result.driver.familyName,
                season = race.season,
                round = race.round,
                raceName = race.raceName,
                circuitId = race.circuit.circuitId,
                circuitName = race.circuit.circuitName,
                country = race.circuit.location.country,
                position = result.position,
                positionText = result.positionText,
                points = result.points,
                grid = result.grid,
                laps = result.laps,
                time = result.time?.time ?: "",
                fastestLap = result.fastestLap?.fastestLapTime?.time ?: "",
                status = result.status
            )
        }
    }
}