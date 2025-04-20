package com.hugo.standings.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hugo.standings.domain.model.DriverStandingsInfo

data class DriverStandingsDto(
    @SerializedName("MRData")
    val mrData: DriverMRData
)

data class DriverMRData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: Int,
    val offset: Int,
    val total: Int,
    @SerializedName("StandingsTable")
    val standingsTable: DriverStandingsTable
)

data class DriverStandingsTable(
    val season: String,
    val round: String,
    @SerializedName("StandingsLists")
    val standingsLists: List<DriverStandingsList>
)

data class DriverStandingsList(
    val season: String,
    val round: String,
    @SerializedName("DriverStandings")
    val driverStandings: List<DriverStanding>
)

data class DriverStanding(
    val position: String,
    val positionText: String,
    val points: String,
    val wins: String,
    @SerializedName("Driver")
    val driver: Driver,
    @SerializedName("Constructors")
    val constructors: List<Constructor>
)

data class Driver(
    val driverId: String,
    val url: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String
)

fun DriverStandingsDto.toDriverStandingsInfoList(): List<DriverStandingsInfo>
{
    val total = mrData.total
    val season = mrData.standingsTable.season
    val round = mrData.standingsTable.round

    return mrData.standingsTable.standingsLists.flatMap {list ->
        list.driverStandings.map {standings ->
            DriverStandingsInfo(
                driverId = standings.driver.driverId,
                total = total,
                season = season,
                round = round,
                position = standings.position,
                points = standings.points,
                wins = standings.wins,
                driverName = standings.driver.givenName + " " + standings.driver.familyName,
                driverNationality = standings.driver.nationality,
                dateOfBirth = standings.driver.dateOfBirth,
                constructorName = standings.constructors.first().name,
                constructorNationality = standings.constructors.first().nationality
            )
        }

    }

}

