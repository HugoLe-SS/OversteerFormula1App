package com.hugo.standings.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DriverDetails(
    val driverId: String,
    val imageUrl: String?= null,
    val firstEntry: String?= null,
    val firstWin: String?= null,
    val firstPodium: String?= null,
    val wdc: Int?= null,
    val about: String?= null,
)