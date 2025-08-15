package com.hugo.result.data.remote.dto.RaceResult

import com.google.gson.annotations.SerializedName

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
