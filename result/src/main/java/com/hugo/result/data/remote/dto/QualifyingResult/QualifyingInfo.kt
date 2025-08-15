package com.hugo.result.data.remote.dto.QualifyingResult

import com.google.gson.annotations.SerializedName
import com.hugo.result.data.remote.dto.Circuit

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