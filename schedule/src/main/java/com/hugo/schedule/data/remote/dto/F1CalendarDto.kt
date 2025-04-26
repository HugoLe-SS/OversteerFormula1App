package com.hugo.schedule.data.remote.dto

import com.google.gson.annotations.SerializedName

data class F1CalendarDto(
    @SerializedName("MRData")
    val mrData: CalendarMrData
)

data class CalendarMrData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: Int,
    val offset: Int,
    val total: Int,
    @SerializedName("RaceTable")
    val raceTable: RaceTable
)

data class RaceTable(
    val season: String,
    @SerializedName("Races")
    val races: List<Race>
)

data class Race(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    @SerializedName("Circuit")
    val circuit: Circuit,
    val date: String,
    val time: String,
    @SerializedName("FirstPractice")
    val firstPractice: SessionInfoDto?,
    @SerializedName("SecondPractice")
    val secondPractice: SessionInfoDto?,
    @SerializedName("ThirdPractice")
    val thirdPractice: SessionInfoDto?,
    @SerializedName("Qualifying")
    val qualifying: SessionInfoDto?,
    @SerializedName("Sprint")
    val sprint: SessionInfoDto?,
    @SerializedName("SprintQualifying")
    val sprintQualifying: SessionInfoDto?

)


data class SessionInfoDto(
    val date: String,
    val time: String?
)





