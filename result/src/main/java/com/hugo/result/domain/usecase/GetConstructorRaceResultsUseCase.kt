package com.hugo.result.domain.usecase

import androidx.paging.PagingData
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.result.domain.repository.IF1ResultRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConstructorRaceResultsUseCase @Inject constructor(
    private val repository: IF1ResultRepository
) {
    operator fun invoke(season: String, constructorId: String): Flow<PagingData<ConstructorRaceResultsInfo>> =
        repository.getConstructorRaceResults(season, constructorId)
}