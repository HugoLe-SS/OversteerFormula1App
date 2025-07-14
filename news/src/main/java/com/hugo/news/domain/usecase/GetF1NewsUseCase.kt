package com.hugo.news.domain.usecase

import com.hugo.news.domain.model.NewsInfo
import com.hugo.news.domain.repository.INewsRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetF1NewsUseCase @Inject constructor(
    private val repository: INewsRepository
) {
    operator fun invoke(): Flow<Resource<List<NewsInfo>, AppError>> = repository.getF1News()

}