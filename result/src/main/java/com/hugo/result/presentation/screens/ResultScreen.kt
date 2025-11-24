package com.hugo.result.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.ErrorDisplayComponent
import com.hugo.design.components.ImageComponent
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.result.presentation.components.CircuitResultBannerComponent
import com.hugo.result.presentation.components.RaceResultListItem
import com.hugo.utilities.logging.AppLogger

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    viewModel: ResultViewModel = hiltViewModel(),
    backButtonClicked : () -> Unit = {},
    driverId: String? = null,
    constructorId: String?= null,
    circuitDetails: F1CircuitDetails? = null
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val driverRaceItems = state.driverRaceResults.collectAsLazyPagingItems()
    val constructorRaceItems = state.constructorRaceResults.collectAsLazyPagingItems()
    val intervals by viewModel.raceIntervals.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = driverId, key2 = constructorId, key3 = circuitDetails){

        if(driverId != null){
            viewModel.fetchDriverRaceResults(
                season = "current",
                driverId = driverId
            )
            AppLogger.d(message = "LaunchedEffect - driverId: $driverId")
        }

       if(constructorId != null)
       {
           viewModel.fetchConstructorRaceResults(
               season = "current",
               constructorId = constructorId
           )
           AppLogger.d(message = "LaunchedEffect - constructorId: $constructorId")
       }

        circuitDetails?.circuitId?.let {
            viewModel.fetchF1CalendarResult(season = "current", circuitId = circuitDetails.circuitId)
            AppLogger.d(message = "LaunchedEffect - circuitId: ${circuitDetails.circuitId}")
        }

    }


    Scaffold (
        topBar = {
            AppToolbar(
                navigationIcon = {
                    IconButton(
                        onClick = {backButtonClicked()}
                    ) {
                        ImageComponent(
                            imageResourceValue = com.hugo.design.R.drawable.ic_back,
                            contentDescription = "Back Button",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colorScheme.background)
                        .padding(innerPadding)
                ) {
                    when {
                        state.isLoading -> {
                            item{
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                    contentAlignment = Alignment.Center
                                ) {
                                    LoadingIndicatorComponent(
                                        paddingValues = innerPadding,
                                        )
                                }
                            }
                        }

                        state.error != null -> {
                            item{
                                ErrorDisplayComponent(
                                    appError = state.error!!,
                                    onRetry = {viewModel.onEvent(ResultEvent.RetryFetch(
                                        season = "current",
                                        driverId = driverId,
                                        constructorId = constructorId,
                                        circuitId = circuitDetails?.circuitId
                                    ))},
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding)
                                )
                            }
                        }

                        else -> {
                            // Circuit Banner
                            item{
                                CircuitResultBannerComponent(
                                    f1CalendarResult = state.f1CalendarResult,
                                    circuitDetails = circuitDetails,
                                )
                            }

                            // Driver Race Results(Paging)
                            driverRaceItems.let { pagingItems ->
                                items(
                                    count = pagingItems.itemCount,
                                    key = { index ->  val item = driverRaceItems[index]
                                        "${item?.round}_${item?.driverId ?: index}"
                                    }
                                ) { index ->
                                    pagingItems[index]?.let { raceResult ->
                                        RaceResultListItem(driverRaceResult = raceResult)
                                    }
                                }
                            }
                            // Constructor Race Results(Paging)
                            constructorRaceItems.let { pagingItems ->
                                items(
                                    count = pagingItems.itemCount,
                                    key = { index ->  val item = constructorRaceItems[index]
                                        "${item?.round}_${item?.driverId ?: index}"
                                    }
                                ) { index ->
                                    pagingItems[index]?.let { raceResult ->
                                        RaceResultListItem(constructorRaceResult = raceResult)
                                    }
                                }
                            }

                            //Circuit Result
                            state.f1CalendarResult?.let { results ->
                                //val intervals = calculateIntervals(results = results)
                                itemsIndexed(
                                    items = results,
                                    key = { _, raceResult -> "circuit_${raceResult.driverId}" }
                                ) { index, raceResult ->
                                    RaceResultListItem(
                                        circuitRaceResult = raceResult,
                                        interval = intervals.getOrNull(index) ?: "Leader" // Use "Leader" for the first item
                                    )
                                }
                            }

                        }
                    }
                }
    }
}
