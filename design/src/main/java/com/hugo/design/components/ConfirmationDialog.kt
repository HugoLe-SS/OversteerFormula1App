package com.hugo.design.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hugo.design.R
import com.hugo.design.ui.theme.AppTheme

@Composable
fun ConfirmationDialog(
    show: Boolean,
    title: String,
    text: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    style = AppTheme.typography.labelNormal
                )
            },
            text = {
                Text(
                    text = text,
                    style = AppTheme.typography.labelMini
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirm,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Red // Make confirm button stand out for destructive actions
                    )
                ) {
                    Text(
                        text = confirmButtonText,
                        style = AppTheme.typography.labelMini
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = AppTheme.typography.labelMini
                    )
                }
            },
            containerColor = AppTheme.colorScheme.onSecondary
        )
    }
}

@Composable
@Preview
fun ConfirmationDialogPreview(){
    AppTheme(
        isDarkTheme = true
    ){
        ConfirmationDialog(
            show = true,
            title = "Sign out",
            text = "Are you certain that you sign out?",
            confirmButtonText = "Sign out",
            onConfirm = {},
            onDismiss = {}
        )
    }
}