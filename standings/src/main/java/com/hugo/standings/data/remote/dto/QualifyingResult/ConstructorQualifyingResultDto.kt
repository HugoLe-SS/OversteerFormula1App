package com.hugo.standings.data.remote.dto.QualifyingResult

import com.google.gson.annotations.SerializedName
import com.hugo.standings.domain.model.ConstructorQualifyingResultsInfo
import com.hugo.standings.domain.model.DriverQualifyingResultsInfo

data class ConstructorQualifyingResultDto(
    @SerializedName("MRData")
    val mrData: ConstructorQualifyingMRData
)

data class ConstructorQualifyingMRData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    @SerializedName("RaceTable")
    val raceTable: ConstructorQRaceTable
)

data class ConstructorQRaceTable(
    val season: String,
    val constructorId: String,
    @SerializedName("Races")
    val qualifying: List<QualifyingInfo>
)

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
