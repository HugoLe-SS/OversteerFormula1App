package com.hugo.standings.domain.usecase

import com.hugo.standings.domain.model.ConstructorQualifyingResultsInfo
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConstructorQualifyingResultsUseCase @Inject constructor(
    private val repository: IF1StandingsRepository
) {
    operator fun invoke(season: String, constructorId: String): Flow<Resource<List<ConstructorQualifyingResultsInfo>>> =
        repository.getConstructorQualifyingResults(season, constructorId)
}