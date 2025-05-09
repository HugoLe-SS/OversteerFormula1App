package com.hugo.utilities.com.hugo.utilities.Navigation.model

import kotlinx.serialization.Serializable

@Serializable
data class DriverClickInfo(
    val driverId: String,
    val constructorName: String,
    val constructorId: String,
    val season: String,
    val givenName: String,
    val familyName: String,
    val driverNumber: String,
    val driverCode: String,
    val position: String,
    val points: String,
    val wins: String,
)
