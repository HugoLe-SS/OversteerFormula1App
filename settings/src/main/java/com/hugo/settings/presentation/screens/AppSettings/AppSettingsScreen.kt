package com.hugo.settings.presentation.screens.AppSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.datasource.local.entity.User.ThemePreference
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.ImageComponent
import com.hugo.design.components.SingleChoiceSegmentedButton
import com.hugo.design.ui.theme.AppTheme
import com.hugo.profile.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSettingsScreen(
    viewModel: AppSettingsViewModel = hiltViewModel(),
    backButtonClicked: () -> Unit = {}
) {

    val state by viewModel.state.collectAsState()

    val themeOptions = listOf(
        ThemePreference.SYSTEM.name,
        ThemePreference.LIGHT.name,
        ThemePreference.DARK.name
    )

    val selectedIndex = when (state.currentTheme) {
        ThemePreference.SYSTEM -> 0
        ThemePreference.LIGHT -> 1
        ThemePreference.DARK -> 2
    }

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
                            text = "App Settings",
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
            Text(
                text = stringResource(R.string.theme_description),
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            SingleChoiceSegmentedButton(
                options = themeOptions,
                selectedIndex = selectedIndex,
                onOptionSelected = { index ->
                    val newTheme = when (index) {
                        1 -> ThemePreference.LIGHT
                        2 -> ThemePreference.DARK
                        else -> ThemePreference.SYSTEM
                    }
                    viewModel.onThemeChanged(newTheme)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )

        }
    }
}