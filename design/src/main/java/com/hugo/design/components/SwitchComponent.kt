package com.hugo.design.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hugo.design.ui.theme.AppTheme

@Composable
fun SwitchComponent(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = AppTheme.colorScheme.background,
            uncheckedThumbColor = AppTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
            checkedTrackColor = AppTheme.colorScheme.onSecondary,
            uncheckedTrackColor = AppTheme.colorScheme.onBackground
        )
    )

}

@Preview
@Composable
fun SwitchComponentPreview() {
    AppTheme(
        isDarkTheme = true
    ){
        SwitchComponent(
            checked = true,
            onCheckedChange = {}
        )
    }


}