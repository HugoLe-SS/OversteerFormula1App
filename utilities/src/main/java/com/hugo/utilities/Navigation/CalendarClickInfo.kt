package com.hugo.utilities.com.hugo.utilities.Navigation

import kotlinx.serialization.Serializable

@Serializable
data class CalendarClickInfo(
    val round: String,
    val circuitId: String
)
