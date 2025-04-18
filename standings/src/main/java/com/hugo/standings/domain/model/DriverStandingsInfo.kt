package com.hugo.standings.domain.model

data class DriverStandingsInfo (
    val total: Int,
    val season: String,
    val round: String,
    val position: String,
    val points: String,
    val wins: String,
    val driverName: String,
    val driverNationality: String,
    val dateOfBirth: String,
    val constructorName: String,
    val constructorNationality: String,
)