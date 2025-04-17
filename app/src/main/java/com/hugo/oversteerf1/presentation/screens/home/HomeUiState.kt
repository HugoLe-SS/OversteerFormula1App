package com.hugo.oversteerf1.presentation.screens.home

import com.hugo.datasource.local.entity.F1News

data class HomeUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val news: List<F1News>? = emptyList()
)
