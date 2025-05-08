package com.hugo.standings.presentation.screens.Details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.design.components.AppToolbar
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.presentation.components.Driver.ConstructorBioList
import com.hugo.standings.presentation.components.Driver.DriverBioList
import com.hugo.standings.presentation.components.Driver.StandingsDetailsBannerComponent
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo
import com.hugo.utilities.logging.AppLogger

@Composable
fun StandingsDetailsScreen(
    constructorClickInfo: ConstructorClickInfo? = null,
    driverClickInfo: DriverClickInfo? = null,
    backButtonClicked : () -> Unit = {},
    viewModel: StandingsDetailsViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = driverClickInfo?.driverId, key2 = constructorClickInfo?.constructorId) {
        AppLogger.d(message = "LaunchedEffect - driverId: ${driverClickInfo?.driverId}, constructorId: ${constructorClickInfo?.constructorId}")

        if (constructorClickInfo?.constructorId != null) {
            AppLogger.d(message = "Calling fetchConstructorDetails")
            viewModel.fetchConstructorDetails(season = "current", constructorId = constructorClickInfo.constructorId)
        } else if (driverClickInfo?.driverId != null) {
            AppLogger.d(message = "Calling fetchDriverDetails")
            viewModel.fetchDriverDetails(season = "current", driverId = driverClickInfo.driverId)
        }
    }

    Scaffold (
        topBar = {
            AppToolbar(
                isBackButtonVisible = true,
                backButtonClicked = backButtonClicked
            )
        },
    )
    {
        padding->

        when{
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        //.padding(innerPadding)
                        .size(24.dp),
                    color = AppTheme.colorScheme.onSecondary
                )
            }
            state.error != null -> {
                Text(text = "Error: ${state.error}")
            }
            else -> {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colorScheme.background)
                        .padding(padding)
                ){

                    constructorClickInfo?.let {
                        item{
                            StandingsDetailsBannerComponent(
                                constructorDetails = state.constructorDetails,
                                constructorClickInfo = constructorClickInfo,
                            )
                        }
                        item {
                            ConstructorBioList(
                                constructorDetails = state.constructorDetails?: return@item,
                            )
                        }
                    }

                    driverClickInfo?.let {
                        item{
                            StandingsDetailsBannerComponent(
                                driverDetails = state.driverDetails,
                                driverClickInfo = driverClickInfo,
                            )
                        }
                        item {
                            DriverBioList(
                                driverDetails = state.driverDetails?: return@item,
                            )
                        }
                    }

                }

            }
        }

    }
}

//                    items(state.driverRaceResults.zip(state.driverQualifyingResults)) { (race, quali) ->
//                        DriverDetailsListItem(
//                            driverRace = race,
//                            driverQuali = quali,
//                        )
//                    }