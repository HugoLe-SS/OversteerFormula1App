package com.hugo.schedule.presentation.screens.Home

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.ui.theme.AppTheme
import com.hugo.schedule.presentation.components.F1CalendarListItem
import com.hugo.utilities.com.hugo.utilities.Navigation.model.CalendarClickInfo

@Composable
fun ScheduleHomeScreen(
    navController: NavHostController,
    viewModel: ScheduleHomeViewModel = hiltViewModel(),
    cardClicked: (CalendarClickInfo) -> Unit = {}
    //cardClicked: (String) -> Unit = {}
){
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            AppToolbar(isHomepage = true)
        },
        bottomBar = {
            BottomNavBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Text(
                text = "Schedule",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(AppTheme.colorScheme.onBackground),
                textAlign = TextAlign.Center,
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colorScheme.onPrimary
            )


            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            //.padding(innerPadding)
                            .size(24.dp),
                        color = AppTheme.colorScheme.onSecondary
                    )
                }
                state.error != null -> {
                    Text(text = "Error: ${state.error}")
                }
                else -> {
                    LazyColumn {
                        items(state.f1Calendar) { race ->
                            F1CalendarListItem(
                                race,
                                cardClicked = { info->
                                    cardClicked(
                                        CalendarClickInfo(
                                            round = info.round,
                                            circuitId = info.circuitId
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
