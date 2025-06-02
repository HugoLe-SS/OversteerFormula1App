package com.hugo.standings.presentation.screens.Home

import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.utilities.AppError

data class StandingsHomeUiState(
    val isLoading: Boolean = true,
    val error: AppError? = null,
    val constructorStandings: List<ConstructorStandingsInfo> = emptyList(),
    val driverStandings: List<DriverStandingsInfo> = emptyList(),
    val currentType: StandingsType = StandingsType.CONSTRUCTOR
)

enum class StandingsType() {
    CONSTRUCTOR,
    DRIVER
}

