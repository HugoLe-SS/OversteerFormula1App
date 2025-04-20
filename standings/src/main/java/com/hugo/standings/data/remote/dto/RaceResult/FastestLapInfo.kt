package com.hugo.standings.data.remote.dto.RaceResult

import com.google.gson.annotations.SerializedName

data class FastestLapInfo(
    val rank: String,
    val lap: String,
    @SerializedName("Time")
    val fastestLapTime: FastestLapTimeInfo
)

