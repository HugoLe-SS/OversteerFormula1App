package com.hugo.oversteerf1.domain.usecase

import com.hugo.datasource.local.entity.F1HomeDetails
import com.hugo.oversteerf1.domain.repository.IF1NewsRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetF1HomeDetailsUseCase @Inject constructor(
    private val repository: IF1NewsRepository
) {
    operator fun invoke(): Flow<Resource<F1HomeDetails?, AppError>> = repository.getF1HomeDetails()
}