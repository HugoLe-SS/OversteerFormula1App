package com.hugo.design.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hugo.design.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChoiceSegmentedButton(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { onOptionSelected(index) },
                selected = index == selectedIndex,
                label = { Text(
                    text = label,
                    style = AppTheme.typography.labelNormal
                ) },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = AppTheme.colorScheme.onSecondary,
                    activeContentColor = AppTheme.colorScheme.background,
                    inactiveContainerColor = AppTheme.colorScheme.background,
                    inactiveContentColor = AppTheme.colorScheme.onSecondary,
                ),
                icon = {}
            )
        }
    }
}



@Preview()
@Composable
fun SingleChoiceSegmentedButtonPreview() {
    AppTheme(isDarkTheme = true) {
        SingleChoiceSegmentedButton(
            options = listOf("Option 1", "Option 2", "Option 3"),
            selectedIndex = 0,
            onOptionSelected = {}
        )
    }
}



