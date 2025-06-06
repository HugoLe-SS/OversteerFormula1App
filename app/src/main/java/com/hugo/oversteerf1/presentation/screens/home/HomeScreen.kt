package com.hugo.oversteerf1.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.components.ErrorDisplayComponent
import com.hugo.design.components.HorizontalPager
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.oversteerf1.presentation.components.NewsListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val imageUrls = listOf(
        "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_lando_team_pic_02.jpg",
        "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_oscar_team_pic_02.jpg",
        "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/hamilton",
        "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/leclerc"
    )


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
                            text = "Home",
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
                    item {
                        Text(
                            text = "Home Screen",
                            style = AppTheme.typography.titleLarge,
                            color = AppTheme.colorScheme.onSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )
                    }

                    item {
                        HorizontalPager(imageUrls = imageUrls)
                    }

                    items(state.news ?: emptyList()) { news ->
                        NewsListItem(news)
                    }

                }
            }

        }
    }
}


//@Composable
//fun HomeScreen(
//    navController: NavHostController,
//    viewModel: HomeViewModel = hiltViewModel()
//) {
//    val state = viewModel.state.value
//
//    Scaffold(
//        topBar = {
//            AppToolbar(isHomepage = true)
//        },
//        bottomBar = {
//            BottomNavBar(
//                navController = navController
//            )
//        }
//    ) { innerPadding ->
//
//        val imageUrls = listOf(
//            "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_lando_team_pic_02.jpg",
//            "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_oscar_team_pic_02.jpg",
//            "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/hamilton",
//            "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/leclerc"
//        )
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .background(AppTheme.colorScheme.background)
//        ) {
//            item {
//                Text(
//                    text = "Home Screen",
//                    style = AppTheme.typography.titleLarge,
//                    color = AppTheme.colorScheme.onSecondary,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp)
//                )
//            }
//
//            item {
//                HorizontalPager(imageUrls = imageUrls)
//            }
//
//            items(state.news ?: emptyList()) { news ->
//                NewsListItem(news)
//            }
//        }
//    }
//}

