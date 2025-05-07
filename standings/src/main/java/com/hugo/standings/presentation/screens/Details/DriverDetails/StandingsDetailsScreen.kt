package com.hugo.standings.presentation.screens.Details.DriverDetails

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
import com.hugo.standings.presentation.components.Driver.DriverBioList
import com.hugo.standings.presentation.components.Driver.StandingsDetailsBannerComponent

@Composable
fun StandingsDetailsScreen(
    driverId: String? = null,
    constructorId: String? = null,
    backButtonClicked : () -> Unit = {},
    viewModel: DriverDetailsViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = driverId, key2 = constructorId) {
        driverId?.let {
            viewModel.fetchDriverDetails(season = "current", driverId = it)
        }

        constructorId?.let {
            viewModel.fetchConstructorDetails(season = "current", constructorId = it)
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
                    item{
                        StandingsDetailsBannerComponent(
                            driver = state.driverRaceResults.firstOrNull() ?: return@item
                        )
                    }

//                    items(state.driverRaceResults.zip(state.driverQualifyingResults)) { (race, quali) ->
//                        DriverDetailsListItem(
//                            driverRace = race,
//                            driverQuali = quali,
//                        )
//                    }

                    item {
                        DriverBioList(
                            driverDetails = state.driverDetails?: return@item,
                            driverRace = state.driverRaceResults.firstOrNull()?: return@item,
                        )
                    }



                }

            }
        }

    }
}