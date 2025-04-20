package com.hugo.standings.data.remote.dto.RaceResult

import com.google.gson.annotations.SerializedName
import com.hugo.standings.domain.model.ConstructorRaceResultsInfo
import com.hugo.standings.domain.model.DriverRaceResultsInfo

data class ConstructorRaceResultDto(
    @SerializedName("MRData")
    val mrData: ConstructorRaceResultMRData
)

data class ConstructorRaceResultMRData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    @SerializedName("RaceTable")
    val raceTable: ConstructorRaceTable
)

data class ConstructorRaceTable(
    val season: String,
    val constructorId: String,
    @SerializedName("Races")
    val races: List<RaceInfo>
)

fun ConstructorRaceResultDto.toConstructorRaceResultInfoList(): List<ConstructorRaceResultsInfo>{
    val total = mrData.total

    return mrData.raceTable.races.flatMap { race ->
        race.results.map{ result ->
            ConstructorRaceResultsInfo(
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