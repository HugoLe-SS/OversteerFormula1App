package com.hugo.design.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hugo.design.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefreshLazyColumn(
    items: List<T>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    key: ((item: T) -> Any)? = null, // Optional key for items
    emptyState: (@Composable () -> Unit)? = null, // Optional empty state
    header: (@Composable () -> Unit)? = null,
    itemContent: @Composable (item: T) -> Unit,
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = AppTheme.colorScheme.onSecondary,
                color = AppTheme.colorScheme.secondary,
                state = state
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (header != null) {
                item {
                    header()
                }
            }

            if (items.isNotEmpty()) {
                items(
                    items = items,
                    key = key
                ) { item ->
                    itemContent(item)
                }
            } else if (emptyState != null && !isRefreshing) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        emptyState()
                    }
                }
            }
        }
    }
}