package com.hugo.standings.domain.model

data class DriverStandingsInfo (
    val driverId: String,
    val total: Int,
    val season: String,
    val round: String,
    val position: String,
    val points: String,
    val wins: String,
    val driverGivenName: String,
    val driverLastName: String,
    val driverNationality: String,
    val dateOfBirth: String,
    val constructorName: String,
    val constructorNationality: String,
)