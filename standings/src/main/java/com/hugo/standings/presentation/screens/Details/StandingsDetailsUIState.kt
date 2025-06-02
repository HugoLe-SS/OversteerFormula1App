package com.hugo.standings.presentation.screens.Details

import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.utilities.AppError

data class StandingsDetailsUIState(
    val isLoading: Boolean = true,
    val error: AppError? = null,
    val driverDetails: DriverDetails? = null,
    val constructorDetails: ConstructorDetails? = null
)
