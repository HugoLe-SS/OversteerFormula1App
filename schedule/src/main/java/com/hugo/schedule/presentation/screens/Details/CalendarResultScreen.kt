package com.hugo.schedule.presentation.screens.Details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.design.components.AppToolbar
import com.hugo.design.ui.theme.AppTheme

@Composable
fun CalendarResultScreen(
    viewModel: CalendarResultViewModel = hiltViewModel(),
    backButtonClicked: () -> Unit = {},
    round: String,
){
    val state by viewModel.state.collectAsState()

    LaunchedEffect (key1 = round){
        viewModel.fetchF1CalendarResult(season = "current", round = round)
    }

    Scaffold (
        topBar = {
            AppToolbar(
                isBackButtonVisible = true,
                backButtonClicked = backButtonClicked
            )
        },
    )
    {padding->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
                .padding(padding)
        ){
            item{
                Text(
                    text = "Calendar Results",
                    style = AppTheme.typography.titleLarge,
                    color = AppTheme.colorScheme.onSecondary
                )
            }

        }
    }

}

