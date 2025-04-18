package com.hugo.standings.presentation.screens.Home.Constructor

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.BottomNavBar
import com.hugo.design.ui.theme.AppTheme

@Composable
fun StandingsHomeScreen(
    navController: NavController,
    viewModel: ConstructorStandingsHomeViewModel = hiltViewModel()
){
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Text(
                text = "Standings",
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
