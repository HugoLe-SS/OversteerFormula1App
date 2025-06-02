package com.hugo.schedule.presentation.screens.Details

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
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.schedule.presentation.components.DetailsScreen.CalendarListItem
import com.hugo.schedule.presentation.components.DetailsScreen.F1CalendarBannerListItem

@Composable
fun CalendarDetailsScreen(
    viewModel: CalendarDetailsViewModel = hiltViewModel(),
    backButtonClicked: () -> Unit = {},
    calendarInfo: F1CalendarInfo,
    viewResultButtonClicked: (F1CircuitDetails)-> Unit = {}
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect (key1 = calendarInfo.circuitId) {
        viewModel.fetchCircuitDetails(circuitId = calendarInfo.circuitId)
    }

    Scaffold (
        topBar = {
            AppToolbar(
                isBackButtonVisible = true,
                backButtonClicked = backButtonClicked
            )
        },
    )
    { padding->
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colorScheme.background)
                        .padding(padding)
                ){
                    when{
                        state.isLoading -> {
                            item{
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(padding),
                                    contentAlignment = Alignment.Center
                                ) {
                                    LoadingIndicatorComponent(
                                        padding = padding
                                    )
                                }
                            }

                        }
                        state.error != null -> {
                            item{
                                Text(text = "Error: ${state.error}")
                            }
                        }
                        else -> {
                            item {
                                state.f1CircuitDetails?.let { circuitDetails ->
                                    F1CalendarBannerListItem(
                                        circuitDetails = circuitDetails,
                                        calendarInfo = calendarInfo,
                                        viewResultButtonClicked = viewResultButtonClicked
                                    )
                                }
                            }

                            item{
                                CalendarListItem(
                                    calendarInfo = calendarInfo,
                                )
                            }

                        }
                    }

                }

    }

}

