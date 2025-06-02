package com.hugo.standings.presentation.screens.Details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.presentation.components.StandingsDetailScreen.ConstructorBioList
import com.hugo.standings.presentation.components.StandingsDetailScreen.DriverBioList
import com.hugo.standings.presentation.components.StandingsDetailScreen.StandingsBannerListItem
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo
import com.hugo.utilities.logging.AppLogger

@Composable
fun StandingsDetailsScreen(
    constructorClickInfo: ConstructorClickInfo? = null,
    driverClickInfo: DriverClickInfo? = null,
    backButtonClicked : () -> Unit = {},
    viewResultButtonClicked: (String) -> Unit = {},
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
            innerPadding ->
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colorScheme.background)
                        .padding(innerPadding)
                ){
                    when{
                        state.isLoading -> {
                            item{
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(innerPadding),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        LoadingIndicatorComponent(
                                            padding = innerPadding
                                        )
                                    }
                                }
                            }
                        }
                        state.error != null -> {
                            item{
                                Text(text = "Error: ${state.error}")
                            }
                        }
                        else-> {
                            constructorClickInfo?.let {
                                item{
                                    StandingsBannerListItem(
                                        constructorDetails = state.constructorDetails,
                                        constructorClickInfo = constructorClickInfo,
                                        buttonClicked = viewResultButtonClicked
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
                                    StandingsBannerListItem(
                                        driverDetails = state.driverDetails,
                                        driverClickInfo = driverClickInfo,
                                        buttonClicked = viewResultButtonClicked
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