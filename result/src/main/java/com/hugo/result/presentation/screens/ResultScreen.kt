package com.hugo.result.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.design.components.AppToolbar
import com.hugo.design.ui.theme.AppTheme
import com.hugo.result.presentation.components.CircuitResultBannerComponent
import com.hugo.result.presentation.components.RaceResultListItem
import com.hugo.result.presentation.components.calculateIntervals
import com.hugo.utilities.logging.AppLogger

@Composable
fun ResultScreen(
    viewModel: ResultViewModel = hiltViewModel(),
    backButtonClicked : () -> Unit = {},
    raceId: String? = null,
    circuitDetails: F1CircuitDetails? = null
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = raceId, key2 = circuitDetails){
        AppLogger.d(message = "LaunchedEffect - raceId: $raceId")
        raceId?.let {
            viewModel.fetchDriverRaceResults(
                season = "current",
                driverId = raceId
            )
            viewModel.fetchConstructorRaceResults(
                season = "current",
                constructorId = raceId
            )
        }

        AppLogger.d(message = "LaunchedEffect - circuitId: ${circuitDetails?.circuitId}")
        circuitDetails?.circuitId?.let {
            viewModel.fetchF1CalendarResult(season = "current", circuitId = circuitDetails.circuitId)
        }

    }


    Scaffold (
        topBar = {
            AppToolbar(
                isBackButtonVisible = true,
                backButtonClicked = backButtonClicked
            )
        },
    ) { padding ->

        when {
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colorScheme.background)
                        .padding(padding)
                ) {
                    item{
                        CircuitResultBannerComponent(
                            f1CalendarResult = state.f1CalendarResult,
                            circuitDetails = circuitDetails,
                        )
                    }

                    state.driverRaceResults?.let{
                        items(state.driverRaceResults) { raceResult ->
                            RaceResultListItem(
                                driverRaceResult = raceResult
                            )
                        }
                    }

                    state.constructorRaceResults?.let{
                        items(state.constructorRaceResults) { raceResult ->
                            RaceResultListItem(
                                constructorRaceResult = raceResult
                            )
                        }
                    }

                    state.f1CalendarResult?.let { results ->
                        val intervals = calculateIntervals(results = results)
                        itemsIndexed(results) { index, raceResult ->
                            RaceResultListItem(
                                circuitRaceResult = raceResult,
                                interval = intervals[index] ?: "Leader" // Use "Leader" for the first item
                            )
                        }
                    }


                }
            }
        }
    }
}
