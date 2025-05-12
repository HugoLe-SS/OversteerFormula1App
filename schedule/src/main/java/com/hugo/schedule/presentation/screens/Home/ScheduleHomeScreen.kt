package com.hugo.schedule.presentation.screens.Home

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.components.SingleChoiceSegmentedButton
import com.hugo.design.ui.theme.AppTheme
import com.hugo.schedule.presentation.components.HomeScreen.F1CalendarListItem

@Composable
fun ScheduleHomeScreen(
    navController: NavHostController,
    viewModel: ScheduleHomeViewModel = hiltViewModel(),
    cardClicked: (F1CalendarInfo) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

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
                        items(state.f1Calendar) { race ->
                            F1CalendarListItem(
                                race,
                                cardClicked = { clickInfo ->
                                    cardClicked(clickInfo)
                                }
                            )
                        }
                    }

                }
            }

        }

}

@Composable
fun SegmentedButton(
    state: ScheduleHomeUiState,
    viewModel: ScheduleHomeViewModel,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
) {
    val selectedIndex = when (state.currentType) {
        ScheduleType.UPCOMING -> 0
        ScheduleType.PAST -> 1
        else -> 0 // Default to the first option
    }
    val options = listOf("Upcoming", "Past")

    SingleChoiceSegmentedButton(
        options = options,
        selectedIndex = selectedIndex,
        onOptionSelected = { index ->
//                viewModel.onEvent(
//                    when (index) {
//                        0 -> ToggleStandingsEvent.SetStandingsType(StandingsType.CONSTRUCTOR)
//                        1 -> ToggleStandingsEvent.SetStandingsType(StandingsType.DRIVER)
//                        else -> ToggleStandingsEvent.SetStandingsType(StandingsType.CONSTRUCTOR)
//                    }
//                )
        },
        modifier = modifier
    )
}



