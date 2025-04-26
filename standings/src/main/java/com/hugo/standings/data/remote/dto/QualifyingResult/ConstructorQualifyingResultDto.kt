package com.hugo.standings.data.remote.dto.QualifyingResult

import com.google.gson.annotations.SerializedName

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

