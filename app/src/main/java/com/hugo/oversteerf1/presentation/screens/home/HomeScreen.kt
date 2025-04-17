package com.hugo.oversteerf1.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hugo.design.components.AppToolbar
import com.hugo.design.ui.theme.AppTheme
import com.hugo.oversteerf1.presentation.screens.navigation.BottomNavBar
import com.hugo.design.components.HorizontalPager
import com.hugo.oversteerf1.presentation.components.NewsListItem


//@Composable
//fun HomeScreen(
//    navController: NavController,
//    viewModel: HomeViewModel = hiltViewModel()
//){
//    val state = viewModel.state.value
//
//    Scaffold (
//        topBar = {
//            AppToolbar(isHomepage = true)
//        },
//        bottomBar = {
//            BottomNavBar(
//                //items = bottomNavItems,
//                navController = navController
//            )
//        }
//    ){
//        innerPadding ->  Column(modifier = Modifier
//        .fillMaxSize()
//        .background(AppTheme.colorScheme.background)
//        .padding(innerPadding),
//    ){
//        val imageUrls = listOf(
//            "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_lando_team_pic_02.jpg",
//            "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_oscar_team_pic_02.jpg",
//            "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/hamilton",
//            "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/leclerc"
//        )
//
//        Text(
//            text = "Home Screen",
//            style = AppTheme.typography.titleLarge,
//            color = AppTheme.colorScheme.onSecondary,
//            textAlign = TextAlign.Center
//
//        )
//
//        HorizontalPager(
//            imageUrls = imageUrls
//        )
//
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(state.news ?: emptyList()) { news ->
//                NewsListItem(news)
//            }
//        }
//    }
//
//    }
//}

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

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

        val imageUrls = listOf(
            "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_lando_team_pic_02.jpg",
            "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_oscar_team_pic_02.jpg",
            "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/hamilton",
            "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/leclerc"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(AppTheme.colorScheme.background)
        ) {
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
