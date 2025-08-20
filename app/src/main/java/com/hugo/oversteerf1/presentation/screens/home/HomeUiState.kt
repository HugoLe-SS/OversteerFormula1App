package com.hugo.oversteerf1.presentation.screens.home

import com.hugo.datasource.local.entity.F1HomeDetails
import com.hugo.datasource.local.entity.F1News
import com.hugo.utilities.AppError

data class HomeUiState(
    val isLoading: Boolean = true,
    val error: AppError? = null,
    val f1HomeDetails: List<F1HomeDetails>?= null,
    val news: List<F1News>? = emptyList()
)