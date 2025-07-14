package com.hugo.oversteerf1.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
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
import com.hugo.design.R.drawable
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.components.ErrorDisplayComponent
import com.hugo.design.components.ImageComponent
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.oversteerf1.presentation.components.HorizontalPagerItem
import com.hugo.oversteerf1.presentation.components.UpcomingRaceCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    cardOnClicked: () -> Unit = {},
    bannerOnClicked: (Int) -> Unit = {},
    onProfileCliclked: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val countdown by viewModel.countdown.collectAsState()
    val upcomingRaceDetail by viewModel.upcomingRaceDetail.collectAsState()

    LaunchedEffect(key1 = state.f1HomeDetails) {
        upcomingRaceDetail?.let {
            viewModel.updateCountdownFromSessions(it)
        }
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
                            text = "Home",
                            style = AppTheme.typography.titleNormal,
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = onProfileCliclked
                        ) {
                            ImageComponent(
                                imageResourceValue = drawable.ic_account
                            )
                        }
                    },

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
                                paddingValues = innerPadding,
                            )
                        }
                    }
                }

                state.error != null -> {
                    item{
                        ErrorDisplayComponent(
                            appError = state.error!!,
                            onRetry = {viewModel.onEvent(HomeEvent.RetryFetch)},
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(16.dp)
                        )
                    }
                }
                else -> {

                    upcomingRaceDetail?.let {
                        item {
                            HorizontalPagerItem(
                                f1HomeDetails = it,
                                onClicked = bannerOnClicked
                            )
                        }

                        item{
                            UpcomingRaceCardItem(
                                cardOnClicked = cardOnClicked,
                                f1HomeDetails = it,
                                countdown = countdown
                            )
                        }
                    }


//                    items(state.news ?: emptyList()) { news ->
//                        NewsListItem(news)
//                    }

                }
            }

        }
    }
}




