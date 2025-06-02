package com.hugo.standings.presentation.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.components.SegmentedButton
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.R
import com.hugo.standings.presentation.components.Driver.StandingsBannerComponent
import com.hugo.standings.presentation.components.StandingsHomeScreen.ConstructorListItem
import com.hugo.standings.presentation.components.StandingsHomeScreen.DriverListItem
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo


@Composable
fun StandingsHomeScreen(
    navController: NavHostController,
    viewModel: StandingsHomeViewModel = hiltViewModel(),
    driverCardClicked: (DriverClickInfo) -> Unit = {},
    constructorCardClicked: (ConstructorClickInfo) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    val selectedIndex = when (state.currentType) {
        StandingsType.CONSTRUCTOR -> 0
        StandingsType.DRIVER -> 1
        else -> 0 // Default to the first option
    }
    val options = listOf("Constructor", "Driver")

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colorScheme.background)
            ) {
                AppToolbar(isStandingsPage = true)
                SegmentedButton(
                    options = options,
                    selectedIndex = selectedIndex,
                    onOptionSelected = { index ->
                        viewModel.onEvent(
                            ToggleStandingsEvent.SetStandingsType(
                                if (index == 0) StandingsType.CONSTRUCTOR else StandingsType.DRIVER
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                navController = navController
            )
        }
    ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(AppTheme.colorScheme.background)
                ) {
                    when{
                        state.isLoading -> {
                            item {
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

                        state.error != null -> {
                            item{
                                Text(
                                    text = "Error: ${state.error}",
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                        }
                        else -> {
                            when (state.currentType) {
                                StandingsType.CONSTRUCTOR -> {
                                    // Constructor Standings
                                    state.constructorStandings.let{ constructors ->
                                        if (constructors.isNotEmpty()) {
                                            item {
                                                StandingsBannerComponent(
                                                    constructorInfo = constructors[0],
                                                    imageUrl = R.drawable.mclaren
                                                )
                                            }
                                        }
                                    }

                                    items(state.constructorStandings) { constructors ->
                                        ConstructorListItem(
                                            constructors,
                                            constructorCardClicked = { info ->
                                                constructorCardClicked(
                                                    ConstructorClickInfo(
                                                        constructorId = info.constructorId,
                                                        constructorName = info.constructorName,
                                                        season = info.season,
                                                        nationality = info.nationality,
                                                        position = info.position,
                                                        points = info.points,
                                                        wins = info.wins
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }

                                StandingsType.DRIVER -> {
                                    // Driver Standings
                                    state.driverStandings.let{ drivers ->
                                        if (drivers.isNotEmpty()) {
                                            item {
                                                StandingsBannerComponent(
                                                    driverInfo = drivers[0],
                                                    imageUrl = R.drawable.lando
                                                )
                                            }
                                        }
                                    }


                                    items(state.driverStandings) { drivers ->
                                        DriverListItem(
                                            drivers,
                                            driverCardClicked = { info ->
                                                driverCardClicked(
                                                    DriverClickInfo(
                                                        driverId = info.driverId,
                                                        constructorName = info.constructorName,
                                                        constructorId = info.constructorId,
                                                        season = info.season,
                                                        givenName = info.givenName,
                                                        familyName = info.familyName,
                                                        driverNumber = info.driverNumber,
                                                        driverCode = info.driverCode,
                                                        position = info.position,
                                                        points = info.points,
                                                        wins = info.wins
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
    }
}


