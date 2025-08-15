package com.hugo.notifications.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.notifications.presentation.components.NotificationCardList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    backButtonClicked: () -> Unit = {}
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
                    navigationIcon = {
                        IconButton(
                            onClick = { backButtonClicked() }
                        ) {
                            ImageComponent(
                                imageResourceValue = com.hugo.design.R.drawable.ic_back,
                                contentDescription = "Back Button",
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Notification",
                            style = AppTheme.typography.titleNormal,
                        )
                    }
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
                .padding(innerPadding),

            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //UI Implementation for Profile Home Screen
            NotificationCardList(
                settings = state.settings,
                onSettingChanged = viewModel::onSettingChanged,
            )

        }
    }
}

@Preview
@Composable
fun NotificationScreenPreview(
){
    AppTheme(
        isDarkTheme = true
    ){
        NotificationScreen()
    }
}