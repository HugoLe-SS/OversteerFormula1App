package com.hugo.design.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hugo.design.R
import com.hugo.design.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null, //Back button...
    actions: @Composable (RowScope.() -> Unit)? = null, // notification icon, settings icon,...
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = AppTheme.colorScheme.background,
        titleContentColor = AppTheme.colorScheme.onSecondary,
        navigationIconContentColor = AppTheme.colorScheme.onSecondary,
        actionIconContentColor = AppTheme.colorScheme.onSecondary
    )
){
    CenterAlignedTopAppBar( // Or TopAppBar, MediumTopAppBar
        title = {
            title?.invoke()
        },
        modifier = modifier, // .systemBarsPadding() is often handled by Scaffold or applied here
        navigationIcon = {
            navigationIcon?.invoke()
        },
        actions = {
            if (actions != null) {
                actions()
            }
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppToolbarPreview() {
    AppTheme(isDarkTheme = true){
        AppToolbar(
            title = {
                Text("My App")
            },
            navigationIcon = {
                    ImageComponent(
                        imageResourceValue = R.drawable.ic_back,
                    )

            },
        )
    }

}