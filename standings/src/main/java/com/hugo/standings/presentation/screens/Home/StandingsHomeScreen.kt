package com.hugo.standings.presentation.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.components.SingleChoiceSegmentedButton
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.presentation.components.ConstructorListItem
import com.hugo.standings.presentation.components.DriverListItem
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo


@Composable
fun StandingsHomeScreen(
    navController: NavHostController,
    viewModel: StandingsHomeViewModel = hiltViewModel(),
    driverCardClicked: (DriverClickInfo) -> Unit = {},
    constructorCardClicked: (ConstructorClickInfo) -> Unit = {}
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colorScheme.background)
            ) {
                AppToolbar(isStandingsPage = true)
                SegmentedButton(
                    state = state,
                    viewModel = viewModel,
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
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = AppTheme.colorScheme.onSecondary
                    )
                }
            }

            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    modifier = Modifier.padding(innerPadding)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(AppTheme.colorScheme.background)
                ) {
                    when (state.currentType) {
                        StandingsType.CONSTRUCTOR -> {
                            // Constructor Standings
                            item{
//                                ConstructorBannerComponent(
//                                    firstDriverGivenName = "Lando",
//                                    firstDriverLastName = "Norris",
//                                    firstDriverNumber = "44",
//                                    secondDriverNumber = "81",
//                                    secondDriverGivenName = "Grabiel",
//                                    secondDriverLastName = "Bortoleto",
//                                    teamName = "McLaren",
//                                    driverImgUrl = "https://mclaren.bloomreach.io/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_lando_team_pic_02.jpg",
//                                    teamImgUrl = "https://media.formula1.com/content/dam/fom-website/teams/2025/mclaren-logo.png"
//                                )
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
                            item{
//                                DriverBannerComponent(
//                                    driverGivenName = "Lando",
//                                    driverLastName = "Norris",
//                                    driverNumber = "44",
//                                    teamName = "McLaren",
//                                    driverImgUrl = "https://mclaren.bloomreach.io/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_lando_team_pic_02.jpg",
//                                    teamImgUrl = "https://media.formula1.com/content/dam/fom-website/teams/2025/mclaren-logo.png"
//                                )
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

@Composable
fun SegmentedButton(
    state: ConstructorStandingsHomeUiState,
    viewModel: StandingsHomeViewModel,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
) {
    val selectedIndex = when (state.currentType) {
        StandingsType.CONSTRUCTOR -> 0
        StandingsType.DRIVER -> 1
        else -> 0 // Default to the first option
    }
    val options = listOf("Constructor", "Driver")

    SingleChoiceSegmentedButton(
        options = options,
        selectedIndex = selectedIndex,
        onOptionSelected = { index ->
            viewModel.onEvent(
                when (index) {
                    0 -> ToggleStandingsEvent.SetStandingsType(StandingsType.CONSTRUCTOR)
                    1 -> ToggleStandingsEvent.SetStandingsType(StandingsType.DRIVER)
                    else -> ToggleStandingsEvent.SetStandingsType(StandingsType.CONSTRUCTOR)
                }
            )
        },
        modifier = modifier
    )
}


