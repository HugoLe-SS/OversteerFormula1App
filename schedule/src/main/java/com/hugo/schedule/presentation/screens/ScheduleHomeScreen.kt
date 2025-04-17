package com.hugo.schedule.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.ui.theme.AppTheme

@Composable
fun ScheduleHomeScreen(
    navController: NavController
){
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

        }

    }

}
