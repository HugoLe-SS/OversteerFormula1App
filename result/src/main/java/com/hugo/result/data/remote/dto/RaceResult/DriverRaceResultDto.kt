package com.hugo.result.data.remote.dto.RaceResult

import com.google.gson.annotations.SerializedName

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


