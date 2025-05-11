package com.hugo.design.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hugo.design.ui.theme.AppTheme

// Mot se cho vao trong AppTopbar

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
                label = { Text(label) },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = AppTheme.colorScheme.onSecondary,
                    activeContentColor = AppTheme.colorScheme.background,
                    inactiveContainerColor = AppTheme.colorScheme.onBackground,
                    inactiveContentColor = AppTheme.colorScheme.secondary,
                ),
            )
        }
    }
}



