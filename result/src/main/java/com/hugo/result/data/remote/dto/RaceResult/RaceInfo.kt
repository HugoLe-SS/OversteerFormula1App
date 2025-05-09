package com.hugo.result.data.remote.dto.RaceResult

import com.google.gson.annotations.SerializedName
import com.hugo.result.data.remote.dto.Circuit

data class RaceInfo(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    @SerializedName("Circuit")
    val circuit: Circuit,
    val date: String,
    val time: String,
    @SerializedName("Results")
    val results: List<RaceResult>
)
