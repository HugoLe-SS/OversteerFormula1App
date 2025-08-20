package com.hugo.standings.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Circuit(
    val circuitId: String,
    val url: String,
    val circuitName: String,
    @SerializedName("Location")
    val location: Location
)