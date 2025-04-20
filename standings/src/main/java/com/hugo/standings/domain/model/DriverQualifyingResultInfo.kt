package com.hugo.standings.domain.model

data class DriverQualifyingResultInfo(
    val total: String,
    val driverNumber: String,
    val driverId: String,
    val constructorId: String,
    val driverCode: String,
    val givenName: String,
    val familyName: String,
    val season: String,
    val round: String,
    val raceName: String,
    val circuitId: String,
    val circuitName: String,
    val country: String,
    val position: String,
    val q1: String?,
    val q2: String?,
    val q3:String?,
)
