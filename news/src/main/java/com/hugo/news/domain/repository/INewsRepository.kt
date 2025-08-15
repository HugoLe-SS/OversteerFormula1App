package com.hugo.news.domain.repository

import com.hugo.news.domain.model.NewsInfo
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getF1News(): Flow<Resource<List<NewsInfo>, AppError>>
}