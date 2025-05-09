package com.hugo.standings.presentation.screens.Details

import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Driver.DriverDetails

data class StandingsDetailsUIState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val driverDetails: DriverDetails? = null,
    val constructorDetails: ConstructorDetails? = null
)
