package com.hugo.standings.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hugo.standings.domain.model.ConstructorStandingsInfo

data class ConstructorStandingsDto(
    @SerializedName("MRData")
    val mrData: ConstructorMRData
)

data class ConstructorMRData(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: Int,
    val offset: Int,
    val total: Int,
    @SerializedName("StandingsTable")
    val standingsTable: ConstructorStandingsTable
)

data class ConstructorStandingsTable(
    val season: String,
    val round: String,
    @SerializedName("StandingsLists")
    val standingsLists: List<ConstructorStandingsList>
)

data class ConstructorStandingsList(
    val season: String,
    val round: String,
    @SerializedName("ConstructorStandings")
    val constructorStandings: List<ConstructorStanding>
)

data class ConstructorStanding(
    val position: String,
    val positionText: String,
    val points: String,
    val wins: String,
    @SerializedName("Constructor")
    val constructor: Constructor
)

fun ConstructorStandingsDto.toConstructorInfoList(): List<ConstructorStandingsInfo> {
    val total = mrData.total
    val season = mrData.standingsTable.season
    val round = mrData.standingsTable.round

    return mrData.standingsTable.standingsLists.flatMap { list ->
        list.constructorStandings.map { standing ->
            ConstructorStandingsInfo(
                total = total,
                season = season,
                round = round,
                position = standing.position,
                points = standing.points,
                wins = standing.wins,
                constructorName = standing.constructor.name,
                constructorNationality = standing.constructor.nationality
            )
        }
    }
}



