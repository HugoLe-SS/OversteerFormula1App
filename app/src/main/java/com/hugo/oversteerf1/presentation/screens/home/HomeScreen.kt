package com.hugo.oversteerf1.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.hugo.design.components.AppToolbar
import com.hugo.design.ui.theme.AppTheme
import com.hugo.oversteerf1.presentation.screens.navigation.BottomNavBar
import com.hugo.design.components.HorizontalPager


@Composable
fun HomeScreen(
    navController: NavController
){

    Scaffold (
        topBar = {
            AppToolbar(isHomepage = true)
        },
        bottomBar = {
            BottomNavBar(
                //items = bottomNavItems,
                navController = navController
            )
        }
    ){
        innerPadding ->  Column(modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colorScheme.background)
        .padding(innerPadding),
    ){
             Text(
                 text = "Home Screen",
                 style = AppTheme.typography.titleLarge,
                 color = AppTheme.colorScheme.onSecondary,
                 textAlign = TextAlign.Center
             )
        val imageUrls = listOf(
            "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_lando_team_pic_02.jpg",
            "https://mclaren.bloomreach.io/cdn-cgi/image/width=1024,quality=80,format=webp/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_oscar_team_pic_02.jpg",
            "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/hamilton",
            "https://media.formula1.com/image/upload/f_auto,c_limit,q_auto,w_1320/content/dam/fom-website/drivers/2025Drivers/leclerc"
        )
        HorizontalPager(
            imageUrls = imageUrls
        )
    }
    }
}