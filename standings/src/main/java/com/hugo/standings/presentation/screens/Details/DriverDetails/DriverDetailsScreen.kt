package com.hugo.standings.presentation.screens.Details.DriverDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.hugo.standings.presentation.components.DriverDetailsBannerComponent
import com.hugo.standings.presentation.components.DriverDetailsListItem

@Composable
fun DriverDetailsScreen(
    driverId: String,
    backButtonClicked : () -> Unit = {},
    viewModel: DriverDetailsViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = driverId) {
        viewModel.fetchDriverDetails(season = "current", driverId = driverId)
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
                        DriverDetailsBannerComponent(
                            driver = state.driverRaceResults.firstOrNull() ?: return@item
                        )
                    }

                    items(state.driverRaceResults.zip(state.driverQualifyingResults)) { (race, quali) ->
                        DriverDetailsListItem(
                            driverRace = race,
                            driverQuali = quali
                        )
                    }
                }

            }
        }

    }
}