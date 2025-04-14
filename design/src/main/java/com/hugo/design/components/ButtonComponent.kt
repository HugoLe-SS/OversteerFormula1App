package com.hugo.design.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.design.ui.theme.AppTheme

@Composable
fun ButtonComponent(
    text: String = "Button",
    buttonColor: Color = AppTheme.colorScheme.secondary,
    buttonClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
   Button(
        onClick = { buttonClicked() },
        modifier = modifier
            .width(120.dp),
        enabled = true,
        shape = AppTheme.shape.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = AppTheme.colorScheme.onSecondary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 6.dp,
            focusedElevation = 6.dp
        ),
    ) {
        Text(
            text = text,
            style = AppTheme.typography.labelSmall,
        )
    }
}

@Preview
@Composable
fun ButtonComponentPreview() {
    AppTheme {
        ButtonComponent()
    }
}
