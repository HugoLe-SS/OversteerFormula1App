package com.hugo.oversteerf1.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.hugo.design.components.AppToolbar
import com.hugo.design.ui.theme.AppTheme
import com.hugo.oversteerf1.R
import com.hugo.oversteerf1.presentation.screens.navigation.BottomNavBar
import com.hugo.oversteerf1.presentation.screens.navigation.BottomNavItem
import com.hugo.oversteerf1.presentation.screens.navigation.Screen

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
    }
    }
}