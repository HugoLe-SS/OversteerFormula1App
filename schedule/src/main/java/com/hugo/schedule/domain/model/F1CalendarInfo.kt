package com.hugo.schedule.domain.model


data class F1CalendarInfo(
    val total: Int,
    val season: String,
    val round: String,
    val circuit: String,
    val mainRaceDate: String,
    val mainRaceTime: String,
    val firstPractice: SessionInfo?,
    val secondPractice: SessionInfo?,
    val thirdPractice: SessionInfo?,
    val qualifying: SessionInfo?,
    val sprintQualifying: SessionInfo?,
    val sprintRace: SessionInfo?
)

data class SessionInfo(
    val date: String,
    val time: String?
)