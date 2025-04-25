package com.hugo.standings.data.remote.dto.RaceResult

import com.google.gson.annotations.SerializedName
import com.hugo.standings.domain.model.DriverRaceResultsInfo

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