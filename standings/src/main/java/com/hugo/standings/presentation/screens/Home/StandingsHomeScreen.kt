package com.hugo.standings.presentation.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.design.R.*
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.components.ErrorDisplayComponent
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.components.PullToRefreshLazyColumn
import com.hugo.design.components.SegmentedButton
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.R
import com.hugo.standings.presentation.components.Driver.StandingsBannerComponent
import com.hugo.standings.presentation.components.StandingsHomeScreen.ConstructorListItem
import com.hugo.standings.presentation.components.StandingsHomeScreen.DriverListItem
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo


@OptIn(ExperimentalMaterial3Api::class)
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
                AppToolbar(
                    title = {
                        Text(
                            text = "Standings",
                            style = AppTheme.typography.titleNormal,
                        )
                    }
                )
                SegmentedButton(
                    options = options,
                    selectedIndex = selectedIndex,
                    onOptionSelected = { index ->
                        viewModel.onEvent(
                            StandingsEvent.SetStandingsType(
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
        when{
            state.isLoading -> {
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

            state.error != null -> {
                ErrorDisplayComponent(
                    appError = state.error!!,
                    onRetry = {viewModel.onEvent(StandingsEvent.RetryFetch)},
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(AppTheme.colorScheme.background)
                )
            }

            else -> {
                val (currentItems: List<Any>, bannerComposable: (@Composable () -> Unit)?) =
                    remember(state.currentType, state.constructorStandings, state.driverStandings) {
                        when (state.currentType) {
                            StandingsType.CONSTRUCTOR -> {
                                val constructors = state.constructorStandings
                                val itemsToDisplay = constructors
                                val banner: (@Composable () -> Unit)? = if (constructors.isNotEmpty()) {
                                    @Composable {
                                        StandingsBannerComponent(
                                            constructorInfo = constructors[0],
                                            imageUrl = R.drawable.mclaren
                                        )
                                    }
                                } else {
                                    null // No banner if the list is empty
                                }
                                Pair(itemsToDisplay, banner)
                            }
                            StandingsType.DRIVER -> {
                                val drivers = state.driverStandings
                                val itemsToDisplay = drivers
                                val banner: (@Composable () -> Unit)? = if (drivers.isNotEmpty()) {
                                    {
                                        StandingsBannerComponent(
                                            driverInfo = drivers[0],
                                            imageUrl = R.drawable.mclaren
                                        )
                                    }
                                } else {
                                    null
                                }
                                Pair(itemsToDisplay, banner)
                            }
                        }
                    }


                PullToRefreshLazyColumn(
                    items = currentItems,
                    isRefreshing = state.isRefreshing,
                    onRefresh = { viewModel.onEvent(StandingsEvent.Refresh) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(AppTheme.colorScheme.background),
                    header = bannerComposable,
                    itemContent = { itemData ->

                        when (itemData) {
                            is ConstructorStandingsInfo -> ConstructorListItem(
                                constructor = itemData,
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
                            is DriverStandingsInfo -> DriverListItem(
                                driver = itemData,
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
                )
            }

        }

    }

}


//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(innerPadding)
//                        .background(AppTheme.colorScheme.background)
//                ) {
//                    when{
//                        state.isLoading -> {
//                            item {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .padding(innerPadding),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    LoadingIndicatorComponent(
//                                        padding = innerPadding
//                                    )
//                                }
//                            }
//                        }
//
//                        state.error != null -> {
//                            item{
//                                ErrorDisplayComponent(
//                                    appError = state.error!!,
//                                    onRetry = {viewModel.onEvent(ToggleStandingsEvent.RetryFetch)},
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .padding(innerPadding)
//                                        .padding(16.dp)
//                                )
//                            }
//                        }
//                        else -> {
//                            when (state.currentType) {
//                                StandingsType.CONSTRUCTOR -> {
//                                    // Constructor Standings
//                                    state.constructorStandings.let{ constructors ->
//                                        if (constructors.isNotEmpty()) {
//                                            item {
//                                                StandingsBannerComponent(
//                                                    constructorInfo = constructors[0],
//                                                    imageUrl = R.drawable.mclaren
//                                                )
//                                            }
//                                        }
//                                    }
//
//                                    items(state.constructorStandings) { constructors ->
//                                        ConstructorListItem(
//                                            constructors,
//                                            constructorCardClicked = { info ->
//                                                constructorCardClicked(
//                                                    ConstructorClickInfo(
//                                                        constructorId = info.constructorId,
//                                                        constructorName = info.constructorName,
//                                                        season = info.season,
//                                                        nationality = info.nationality,
//                                                        position = info.position,
//                                                        points = info.points,
//                                                        wins = info.wins
//                                                    )
//                                                )
//                                            }
//                                        )
//                                    }
//                                }
//
//                                StandingsType.DRIVER -> {
//                                    // Driver Standings
//                                    state.driverStandings.let{ drivers ->
//                                        if (drivers.isNotEmpty()) {
//                                            item {
//                                                StandingsBannerComponent(
//                                                    driverInfo = drivers[0],
//                                                    imageUrl = R.drawable.lando
//                                                )
//                                            }
//                                        }
//                                    }
//
//
//                                    items(state.driverStandings) { drivers ->
//                                        DriverListItem(
//                                            drivers,
//                                            driverCardClicked = { info ->
//                                                driverCardClicked(
//                                                    DriverClickInfo(
//                                                        driverId = info.driverId,
//                                                        constructorName = info.constructorName,
//                                                        constructorId = info.constructorId,
//                                                        season = info.season,
//                                                        givenName = info.givenName,
//                                                        familyName = info.familyName,
//                                                        driverNumber = info.driverNumber,
//                                                        driverCode = info.driverCode,
//                                                        position = info.position,
//                                                        points = info.points,
//                                                        wins = info.wins
//                                                    )
//                                                )
//                                            }
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                }

//        val itemsToShow: List<Any> = when (state.currentType) {
//            StandingsType.CONSTRUCTOR -> state.constructorStandings
//            StandingsType.DRIVER -> state.driverStandings
//        }