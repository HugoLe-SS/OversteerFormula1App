package com.hugo.standings.presentation.screens.Details.DriverDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.design.components.AppToolbar
import com.hugo.design.ui.theme.AppTheme

@Composable
fun DriverDetailsScreen(
    driverId: String,
    backButtonClicked : () -> Unit = {},
    viewModel: DriverDetailsViewModel = hiltViewModel()
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = driverId) {
        viewModel.fetchDriverDetails(season = "current", driverId = driverId)
    }

    Scaffold (
        topBar = {
            AppToolbar(
                isBackButtonVisible = true,
                backButtonClicked = backButtonClicked
            )
        },
    )
    {
        padding->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
                .padding(padding)
        ){
            item{
                Text(
                    text = "Driver Details Screen",
                    style = AppTheme.typography.titleLarge,
                    color = AppTheme.colorScheme.onSecondary
                )
            }
        }
    }
}