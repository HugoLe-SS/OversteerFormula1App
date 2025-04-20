package com.hugo.standings.data.remote.dto.QualifyingResult

import com.google.gson.annotations.SerializedName
import com.hugo.standings.data.remote.dto.Constructor
import com.hugo.standings.data.remote.dto.DriverInfo

data class QualifyingResult(
    val number: String,
    val position: String,
    @SerializedName("Driver")
    val driver: DriverInfo,
    @SerializedName("Constructor")
    val constructor: Constructor,
    @SerializedName("Q1")
    val q1: String?,
    @SerializedName("Q2")
    val q2: String?,
    @SerializedName("Q3")
    val q3: String?
)
