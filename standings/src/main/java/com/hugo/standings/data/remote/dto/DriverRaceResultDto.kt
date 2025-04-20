package com.hugo.standings.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hugo.standings.domain.model.DriverRaceResultInfo

data class DriverRaceResultDto(
    @SerializedName("MRData")
    val mrData: RaceResultMRData
)

data class RaceResultMRData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    @SerializedName("RaceTable")
    val raceTable: RaceTable
)

data class RaceTable(
    val season: String,
    val driverId: String,
    @SerializedName("Races")
    val races: List<RaceInfo>
)

data class RaceInfo(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    @SerializedName("Circuit")
    val circuit: Circuit,
    val date: String,
    val time: String,
    @SerializedName("Results")
    val results: List<RaceResult>
)



data class RaceResult(
    val number: String,
    val position: String,
    val positionText: String,
    val points: String,
    @SerializedName("Driver")
    val driver: DriverInfo,
    @SerializedName("Constructor")
    val constructor: Constructor,
    val grid: String,
    val laps: String,
    val status: String,
    @SerializedName("Time")
    val time: TimeInfo?,
    @SerializedName("FastestLap")
    val fastestLap: FastestLapInfo?
)

data class DriverInfo(
    val driverId: String,
    val permanentNumber: String,
    val code: String,
    val url: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String
)


data class TimeInfo (
    val millis: String,
    val time: String
)

data class FastestLapInfo(
    val rank: String,
    val lap: String,
    @SerializedName("Time")
    val fastestLapTime: FastestLapTimeInfo
)

data class FastestLapTimeInfo (
    val time: String
)

fun DriverRaceResultDto.toDriverRaceResultInfoList(): List<DriverRaceResultInfo>{
    val total = mrData.total

    return mrData.raceTable.races.flatMap { race ->
        race.results.map{ result ->
            DriverRaceResultInfo(
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