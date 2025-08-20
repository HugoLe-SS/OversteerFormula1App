package com.hugo.design.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.design.ui.theme.AppTheme

@Composable
fun StyledOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        singleLine = singleLine,

        textStyle = TextStyle(
            color = AppTheme.colorScheme.onSecondary,
        ),

        // This line will now compile correctly because of the @OptIn annotation
        colors = TextFieldDefaults.colors(
            // Use specific container colors for each state
            focusedContainerColor = AppTheme.colorScheme.onBackground.copy(alpha = 0.4f),
            unfocusedContainerColor = AppTheme.colorScheme.onBackground.copy(alpha = 0.4f),

            // Border colors
            focusedIndicatorColor = AppTheme.colorScheme.onSecondary.copy(alpha = 0.7f),
            unfocusedIndicatorColor = AppTheme.colorScheme.onSecondary.copy(alpha = 0.7f),

            // Cursor color
            cursorColor = AppTheme.colorScheme.primary,

            // Label colors for each state
            focusedLabelColor = AppTheme.colorScheme.onSecondary,
            unfocusedLabelColor = AppTheme.colorScheme.onSecondary
        )
    )
}

@Preview
@Composable
fun StyledOutlinedTextFieldPreview(){
    AppTheme(
        isDarkTheme = true
    ){
        StyledOutlinedTextField(
            value = "John Doe",
            onValueChange = {},
            label = "Display Name"
        )
    }
}