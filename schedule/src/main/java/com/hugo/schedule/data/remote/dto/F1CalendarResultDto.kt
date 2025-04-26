package com.hugo.schedule.data.remote.dto

import com.google.gson.annotations.SerializedName

data class F1CalendarResultDto(
    @SerializedName("MRData")
    val mrData: F1CalendarResultMrData
)

data class F1CalendarResultMrData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: Int,
    val offset: Int,
    val total: Int,
    @SerializedName("RaceTable")
    val raceTable: F1CalendarResultRaceTable
)

data class F1CalendarResultRaceTable(
    val season: String,
    val round: String,
    @SerializedName("Races")
    val races: List<DriverRaceResultRace>
)

data class DriverRaceResultRace(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    @SerializedName("Circuit")
    val circuit: Circuit,
    val date: String,
    val time: String,
    @SerializedName("Results")
    val results: List<DriverRaceResult>
)

data class DriverRaceResult(
    val number: String,
    val position: String,
    val positionText: String,
    val points: String,
    @SerializedName("Driver")
    val driver: DriverInfo,
    @SerializedName("Constructor")
    val constructor: ConstructorInfo,
    val grid: String,
    val laps: String,
    val status: String,
    @SerializedName("Time")
    val time: RaceTime?,
    @SerializedName("FastestLap")
    val fastestLap: FastestLap?
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

data class ConstructorInfo(
    val constructorId: String,
    val url: String,
    val name: String,
    val nationality: String
)

data class RaceTime(
    val millis: String?,
    val time: String
)

data class FastestLap(
    val rank: String,
    val lap: String,
    @SerializedName("Time")
    val fastestLapTime: FastestLapTime
)

data class FastestLapTime(
    val time: String
)