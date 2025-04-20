package com.hugo.standings.presentation.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.presentation.components.ConstructorListItem
import com.hugo.standings.presentation.components.DriverListItem
import com.hugo.standings.presentation.components.SegmentedButton

@Composable
fun StandingsHomeScreen(
    navController: NavController,
    viewModel: StandingsHomeViewModel = hiltViewModel(),
    driverCardClicked: (String) -> Unit = {},
    constructorCardClicked: (String) -> Unit = {}
){
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            AppToolbar(isStandingsPage = true)
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

            item{
                ToggleButton(state= state, viewModel = viewModel)
            }

            when (state.currentType) {
                StandingsType.CONSTRUCTOR -> {
                        items(state.constructorStandings ?: emptyList()) { constructors ->
                            ConstructorListItem(
                                constructors,
                                constructorCardClicked = { constructorId ->
                                    constructorCardClicked(constructorId)
                                }

                            )
                    }
                }
                StandingsType.DRIVER -> {
                        items(state.driverStandings ?: emptyList()) { drivers ->
                            DriverListItem(
                                drivers,
                                driverCardClicked = { driverId ->
                                    driverCardClicked(driverId)
                                }
                            )
                    }
                }
            }


        }
    }
}

@Composable
fun ToggleButton(state: ConstructorStandingsHomeUiState,
           viewModel: StandingsHomeViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val selectedType = state.currentType

        SegmentedButton(
            text = "Constructor",
            selected = selectedType == StandingsType.CONSTRUCTOR,
            onClick = {
                if (selectedType != StandingsType.CONSTRUCTOR) {
                    viewModel.onEvent(ToggleStandingsEvent.SetStandingsType(StandingsType.CONSTRUCTOR))
                }
            }
        )
        SegmentedButton(
            text = "Driver",
            selected = selectedType == StandingsType.DRIVER,
            onClick = {
                if (selectedType != StandingsType.DRIVER) {
                    viewModel.onEvent(ToggleStandingsEvent.SetStandingsType(StandingsType.DRIVER))
                }
            }
        )
    }
}
