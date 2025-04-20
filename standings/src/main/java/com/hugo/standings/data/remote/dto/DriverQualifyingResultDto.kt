package com.hugo.standings.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hugo.standings.domain.model.DriverQualifyingResultInfo

data class DriverQualifyingResultDto(
    @SerializedName("MRData")
    val mrData: DriverQualifyingMRData
)

data class DriverQualifyingMRData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    @SerializedName("RaceTable")
    val raceTable: QRaceTable
)

data class QRaceTable(
    val season: String,
    val driverId: String,
    @SerializedName("Races")
    val qualifying: List<QualifyingInfo>
)

data class QualifyingInfo(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    @SerializedName("Circuit")
    val circuit: Circuit,
    val date: String,
    val time: String,
    @SerializedName("QualifyingResults")
    val results: List<QualifyingResult>
)

data class QualifyingResult(
    val number: String,
    val position: String,
    @SerializedName("Driver")
    val driver: DriverInfo,
    @SerializedName("Constructor")
    val constructor: Constructor,
    @SerializedName("Q1")
    val q1: String?,
    @SerializedName("Q2")
    val q2: String?,
    @SerializedName("Q3")
    val q3: String?
)

fun DriverQualifyingResultDto.toDriverQualifyingResultInfoList(): List<DriverQualifyingResultInfo> {
    val total = mrData.total

    return mrData.raceTable.qualifying.flatMap { race ->
        race.results.map { result ->
            DriverQualifyingResultInfo(
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

