package com.hugo.schedule.presentation.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.components.ErrorDisplayComponent
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.components.PullToRefreshLazyColumn
import com.hugo.design.components.SingleChoiceSegmentedButton
import com.hugo.design.ui.theme.AppTheme
import com.hugo.schedule.presentation.components.HomeScreen.F1CalendarListItem
import com.hugo.schedule.presentation.components.HomeScreen.ScheduleBannerComponent

@OptIn(ExperimentalMaterial3Api::class)
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
                    .background(AppTheme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppToolbar(
                    title = {
                        Text(
                            text = "Schedule",
                            style = AppTheme.typography.titleNormal,
                        )
                    }
                )
                SingleChoiceSegmentedButton(
                    options = options,
                    selectedIndex = selectedIndex,
                    onOptionSelected = { index ->
                        viewModel.onEvent(
                            ScheduleEvent.SetScheduleType(
                                if (index == 0) ScheduleType.UPCOMING else ScheduleType.PAST
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
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
                    onRetry = {viewModel.onEvent(ScheduleEvent.RetryFetch)},
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(AppTheme.colorScheme.background)
                )
            }

            else -> {
                val (currentItems: List<Any>, bannerComposable: (@Composable () -> Unit)?) =
                    remember(state.currentType, filteredEvents, countdown) {
                        when (state.currentType) {
                            ScheduleType.UPCOMING, ScheduleType.PAST-> { // Combine if logic is same
                                val banner: (@Composable () -> Unit)? = if (filteredEvents.isNotEmpty()) {
                                    @Composable {
                                        ScheduleBannerComponent(
                                            calendarInfo = filteredEvents[0],
                                            countdown = countdown,
                                            buttonClicked = { clickInfo ->
                                                viewScheduleButtonClicked(clickInfo) },
                                            currentType = state.currentType
                                        )
                                    }
                                } else {
                                    null // No banner if the list is empty
                                }
                                Pair(filteredEvents, banner)
                            }
//                            ScheduleType.PAST -> {
//                                val banner: (@Composable () -> Unit)? = if (filteredEvents.isNotEmpty()) {
//                                    {
//                                        ScheduleBannerComponent(
//                                            calendarInfo = filteredEvents[0],
//                                            countdown = countdown,
//                                            buttonClicked = { clickInfo ->
//                                                viewScheduleButtonClicked(clickInfo) },
//                                            currentType = state.currentType
//                                        )
//                                    }
//                                } else {
//                                    null
//                                }
//                                Pair(filteredEvents, banner)
//                            }
                        }
                    }


                PullToRefreshLazyColumn(
                    items = trimmedFilteredEvents,
                    isRefreshing = state.isRefreshing,
                    onRefresh = { viewModel.onEvent(ScheduleEvent.Refresh) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(AppTheme.colorScheme.background),
                    header = bannerComposable,
                    itemContent = { f1CalendarItem ->
                        F1CalendarListItem(
                            calendarInfo = f1CalendarItem,
                            cardClicked = { clickInfo ->
                                cardClicked(clickInfo)
                            }
                        )
                    },
                )

            }
        }

//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding)
//                            .background(AppTheme.colorScheme.background)
//                    ) {
//                        when {
//                            state.isLoading -> {
//                                item{
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .padding(innerPadding),
//                                        contentAlignment = Alignment.Center
//                                    ) {
//                                        LoadingIndicatorComponent(
//                                            paddingValues = innerPadding,
//
//                                            )
//                                    }
//                                }
//                            }
//
//                            state.error != null -> {
//                                item{
//                                    ErrorDisplayComponent(
//                                        appError = state.error!!,
//                                        onRetry = {viewModel.onEvent(ToggleScheduleEvent.RetryFetch)},
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .padding(innerPadding)
//                                    )
//                                }
//                            }
//
//                            else -> {
//                                when(state.currentType){
//                                    ScheduleType.UPCOMING ->{
//                                        filteredEvents.let{ calendar ->
//                                            item{
//                                                ScheduleBannerComponent(
//                                                    calendarInfo = calendar[0],
//                                                    countdown = countdown,
//                                                    buttonClicked = { clickInfo ->
//                                                        viewScheduleButtonClicked(clickInfo)
//                                                    },
//                                                    currentType = state.currentType
//                                                )
//                                            }
//                                        }
//                                    }
//
//                                    ScheduleType.PAST ->{
//                                        filteredEvents.let{ calendar ->
//                                            item{
//                                                ScheduleBannerComponent(
//                                                    calendarInfo = calendar[0],
//                                                    countdown = countdown,
//                                                    buttonClicked = { clickInfo ->
//                                                        viewScheduleButtonClicked(clickInfo)
//                                                    },
//                                                    currentType = state.currentType,
//                                                )
//                                            }
//                                        }
//                                    }
//                                }
//
//
//                                items(trimmedFilteredEvents) { race ->
//                                    F1CalendarListItem(
//                                        race,
//                                        cardClicked = { clickInfo ->
//                                            cardClicked(clickInfo)
//                                        }
//                                    )
//                                }
//                            }
//
//                        }
//
//                    }

    }

}




