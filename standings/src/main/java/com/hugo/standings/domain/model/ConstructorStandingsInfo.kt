package com.hugo.standings.domain.model

data class ConstructorStandingsInfo(
    val constructorId: String,
    val total: Int,
    val season: String,
    val round: String,
    val position: String,
    val points: String,
    val wins: String,
    val constructorName: String,
    val constructorNationality: String
)
