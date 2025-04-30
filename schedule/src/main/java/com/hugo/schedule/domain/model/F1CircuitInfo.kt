package com.hugo.schedule.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class F1CircuitInfo(
    val circuitId: String,
    val imageUrl: String?= null,
    val circuitDescription: String?= null,
    val circuitFacts: List<String>?= null,
)