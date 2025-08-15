package com.hugo.standings.domain.usecase

import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConstructorDetailsUseCase @Inject constructor(
    private val repository: IF1StandingsRepository
) {
    operator fun invoke(constructorId: String): Flow<Resource<ConstructorDetails?, AppError>> = repository.getF1ConstructorDetails(constructorId)
}