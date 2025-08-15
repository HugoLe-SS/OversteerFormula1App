package com.hugo.standings.data.mapper

import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.standings.data.remote.dto.Standings.ConstructorStandingsDto
import com.hugo.standings.data.remote.dto.Standings.DriverStandingsDto

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