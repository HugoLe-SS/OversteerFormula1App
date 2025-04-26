package com.hugo.standings.data.remote.dto.QualifyingResult

import com.google.gson.annotations.SerializedName

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



