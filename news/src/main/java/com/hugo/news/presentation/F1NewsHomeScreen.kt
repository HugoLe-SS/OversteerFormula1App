package com.hugo.news.presentation

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
import com.hugo.design.components.ErrorDisplayComponent
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.components.PullToRefreshLazyColumn
import com.hugo.design.ui.theme.AppTheme
import com.hugo.news.presentation.components.ArticlesList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun F1NewsHomeScreen(
    navController: NavHostController,
    viewModel: F1NewsViewModel = hiltViewModel(),
    cardClicked: (F1CalendarInfo) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()


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
                            text = "News",
                            style = AppTheme.typography.titleNormal,
                        )
                    }
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
                    LoadingIndicatorComponent(
                        paddingValues = innerPadding,
                    )
                }
            }

            state.error != null && state.newsList.isEmpty() -> {
                ErrorDisplayComponent(
                    appError = state.error!!,
                    onRetry = { viewModel.onRefresh() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(AppTheme.colorScheme.background)
                )
            }

            else -> {
                PullToRefreshLazyColumn(
                    items = state.newsList,
                    isRefreshing = state.isRefreshing,
                    onRefresh = { viewModel.onRefresh() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(AppTheme.colorScheme.background),
                    key = { newsInfo -> newsInfo.webUrl }, // Using webUrl as unique key
                    header = {
                        Text(
                            text = "Latest News",
                            style = AppTheme.typography.titleNormal,
                            color = AppTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                    itemContent = { newsInfo ->
                        ArticlesList(
                            newsInfo = newsInfo,
                            onArticleClick = {
                                // Handle article click - navigate to article detail
                                // You can pass the newsInfo or webUrl to navigate
                                // navController.navigate("article_detail/${newsInfo.webUrl}")
                            }
                        )
                    }
                )

            }
        }
    }
}