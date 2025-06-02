package com.hugo.schedule.presentation.screens.Home

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
import androidx.compose.runtime.LaunchedEffect
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
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.components.SegmentedButton
import com.hugo.design.ui.theme.AppTheme
import com.hugo.schedule.presentation.components.HomeScreen.F1CalendarListItem
import com.hugo.schedule.presentation.components.HomeScreen.ScheduleBannerComponent

@Composable
fun ScheduleHomeScreen(
    navController: NavHostController,
    viewModel: ScheduleHomeViewModel = hiltViewModel(),
    viewScheduleButtonClicked: (F1CalendarInfo) -> Unit = {},
    cardClicked: (F1CalendarInfo) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val filteredEvents by viewModel.filteredEvents.collectAsState()
    val trimmedFilteredEvents by viewModel.trimmedFilteredEvents.collectAsState()
    val countdown by viewModel.countdown.collectAsState()

    LaunchedEffect(key1 = filteredEvents.firstOrNull()) {
        filteredEvents.firstOrNull()?.let {
            viewModel.updateCountdownFromSessions(it)
        }
    }

    val options = listOf("Upcoming", "Past")
    val selectedIndex = when (state.currentType) {
        ScheduleType.UPCOMING -> 0
        ScheduleType.PAST -> 1
        else -> 0
    }

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
                            ToggleScheduleEvent.SetScheduleType(
                                if (index == 0) ScheduleType.UPCOMING else ScheduleType.PAST
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
                                when(state.currentType){
                                    ScheduleType.UPCOMING ->{
                                        filteredEvents.let{ calendar ->
                                            item{
                                                ScheduleBannerComponent(
                                                    calendarInfo = calendar[0],
                                                    countdown = countdown,
                                                    buttonClicked = { clickInfo ->
                                                        viewScheduleButtonClicked(clickInfo)
                                                    },
                                                    currentType = state.currentType
                                                )
                                            }
                                        }
                                    }

                                    ScheduleType.PAST ->{
                                        filteredEvents.let{ calendar ->
                                            item{
                                                ScheduleBannerComponent(
                                                    calendarInfo = calendar[0],
                                                    countdown = countdown,
                                                    buttonClicked = { clickInfo ->
                                                        viewScheduleButtonClicked(clickInfo)
                                                    },
                                                    currentType = state.currentType,
                                                )
                                            }
                                        }
                                    }
                                }


                                items(trimmedFilteredEvents) { race ->
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




