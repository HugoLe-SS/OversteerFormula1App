package com.hugo.account.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hugo.account.presentation.components.ProfileCardType
import com.hugo.account.presentation.components.ProfileScreenCardList
import com.hugo.design.components.AppToolbar
import com.hugo.design.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileHomeScreen(
    cardOnClicked: (ProfileCardType) -> Unit = {},
) {

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
                            text = "Settings",
                            style = AppTheme.typography.titleNormal,
                        )
                    }
                )
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(AppTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //UI Implementation for Profile Home Screen

            item{
                ProfileScreenCardList(
                    cardOnClicked = cardOnClicked
                )
            }

        }
    }
}

@Preview
@Composable
fun ProfileHomeScreenPreview(){
    AppTheme(isDarkTheme = true){
        ProfileHomeScreen(
            cardOnClicked = {},
        )
    }

}