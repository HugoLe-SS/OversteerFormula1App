package com.hugo.news.presentation

import com.hugo.news.domain.model.NewsInfo
import com.hugo.utilities.AppError

data class F1NewsUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val newsList: List<NewsInfo> = emptyList(),
    val error: AppError? = null

)
