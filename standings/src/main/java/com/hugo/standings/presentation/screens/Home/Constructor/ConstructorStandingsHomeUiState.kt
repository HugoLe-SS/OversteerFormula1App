package com.hugo.standings.presentation.screens.Home.Constructor

import com.hugo.standings.domain.model.ConstructorStandingsInfo

data class ConstructorStandingsHomeUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val constructorStandings: List<ConstructorStandingsInfo>? = null,
)
