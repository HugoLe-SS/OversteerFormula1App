package com.hugo.standings.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ConstructorDetails(
    val constructorId: String,
    val imageUrl: String?= null,
    val chassis: String?= null,
    val powerUnit: String?= null,
    val teamPrincipal: String?= null,
    val firstEntry: String?= null,
    val wcc: Int?= null,
    val wdc: Int?= null,
    val about:String?= null,
)
