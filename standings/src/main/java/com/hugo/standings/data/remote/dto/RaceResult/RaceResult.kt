package com.hugo.standings.data.remote.dto.RaceResult

import com.google.gson.annotations.SerializedName
import com.hugo.standings.data.remote.dto.Constructor
import com.hugo.standings.data.remote.dto.DriverInfo

data class RaceResult(
    val number: String,
    val position: String,
    val positionText: String,
    val points: String,
    @SerializedName("Driver")
    val driver: DriverInfo,
    @SerializedName("Constructor")
    val constructor: Constructor,
    val grid: String,
    val laps: String,
    val status: String,
    @SerializedName("Time")
    val time: ResultTime?,
    @SerializedName("FastestLap")
    val fastestLap: FastestLapInfo?
)
