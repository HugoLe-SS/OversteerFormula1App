package com.hugo.standings.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hugo.design.ui.theme.AppTheme

// Mot se cho vao trong AppTopbar

@Composable
fun SegmentedButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = AppTheme.shape.button,
        colors = if (selected) {
            ButtonDefaults.buttonColors(
                containerColor = AppTheme.colorScheme.onSecondary,
                contentColor = AppTheme.colorScheme.background
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = AppTheme.colorScheme.onBackground,
                contentColor = AppTheme.colorScheme.secondary
            )
        },
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = text,
            style = AppTheme.typography.body,
        )
    }
}