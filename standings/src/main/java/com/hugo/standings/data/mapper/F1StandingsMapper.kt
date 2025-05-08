package com.hugo.standings.data.mapper

import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.standings.data.remote.dto.QualifyingResult.ConstructorQualifyingResultDto
import com.hugo.standings.data.remote.dto.QualifyingResult.DriverQualifyingResultDto
import com.hugo.standings.data.remote.dto.RaceResult.ConstructorRaceResultDto
import com.hugo.standings.data.remote.dto.RaceResult.DriverRaceResultDto
import com.hugo.standings.data.remote.dto.Standings.ConstructorStandingsDto
import com.hugo.standings.data.remote.dto.Standings.DriverStandingsDto

fun ConstructorRaceResultDto.toConstructorRaceResultInfoList(): List<ConstructorRaceResultsInfo>{
    val total = mrData.total

    return mrData.raceTable.races.flatMap { race ->
        race.results.map{ result ->
            ConstructorRaceResultsInfo(
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

fun DriverRaceResultDto.toDriverRaceResultInfoList(): List<DriverRaceResultsInfo>{
    val total = mrData.total

    return mrData.raceTable.races.flatMap { race ->
        race.results.map{ result ->
            DriverRaceResultsInfo(
                total = total,
                driverNumber = result.number,
                driverId = result.driver.driverId,
                constructorId = result.constructor.constructorId,
                constructorName = result.constructor.name,
                driverCode = result.driver.code,
                givenName = result.driver.givenName,
                familyName = result.driver.familyName,
                dateOfBirth = result.driver.dateOfBirth,
                nationality = result.driver.nationality,
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

fun ConstructorQualifyingResultDto.toConstructorQualifyingResultInfoList(): List<ConstructorQualifyingResultsInfo> {
    val total = mrData.total

    return mrData.raceTable.qualifying.flatMap { race ->
        race.results.map { result ->
            ConstructorQualifyingResultsInfo(
                total = total,
                driverNumber = result.number,
                driverId = result.driver.driverId,
                constructorId = result.constructor.constructorId,
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
                q1 = result.q1,
                q2 = result.q2,
                q3 = result.q3
            )
        }
    }
}

fun DriverQualifyingResultDto.toDriverQualifyingResultInfoList(): List<DriverQualifyingResultsInfo> {
    val total = mrData.total

    return mrData.raceTable.qualifying.flatMap { race ->
        race.results.map { result ->
            DriverQualifyingResultsInfo(
                total = total,
                driverNumber = result.number,
                driverId = result.driver.driverId,
                constructorId = result.constructor.constructorId,
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
                q1 = result.q1,
                q2 = result.q2,
                q3 = result.q3
            )
        }
    }

}

fun ConstructorStandingsDto.toConstructorInfoList(): List<ConstructorStandingsInfo> {
    val total = mrData.total
    val season = mrData.standingsTable.season
    val round = mrData.standingsTable.round

    return mrData.standingsTable.standingsLists.flatMap { list ->
        list.constructorStandings.map { standing ->
            ConstructorStandingsInfo(
                constructorId = standing.constructor.constructorId,
                total = total,
                season = season,
                round = round,
                position = standing.position,
                points = standing.points,
                wins = standing.wins,
                constructorName = standing.constructor.name,
                constructorNationality = standing.constructor.nationality
            )
        }
    }
}

fun DriverStandingsDto.toDriverStandingsInfoList(): List<DriverStandingsInfo> {
    val total = mrData.total
    val season = mrData.standingsTable.season
    val round = mrData.standingsTable.round

    return mrData.standingsTable.standingsLists.flatMap {list ->
        list.driverStandings.map {standings ->
            DriverStandingsInfo(
                driverId = standings.driver.driverId,
                constructorId = standings.constructors.first().constructorId,
                total = total,
                season = season,
                round = round,
                position = standings.position,
                points = standings.points,
                wins = standings.wins,
                driverGivenName = standings.driver.givenName,
                driverLastName = standings.driver.familyName,
                driverNumber = standings.driver.permanentNumber,
                driverCode = standings.driver.code,
                driverNationality = standings.driver.nationality,
                dateOfBirth = standings.driver.dateOfBirth,
                constructorName = standings.constructors.first().name,
                constructorNationality = standings.constructors.first().nationality
            )
        }

    }

}