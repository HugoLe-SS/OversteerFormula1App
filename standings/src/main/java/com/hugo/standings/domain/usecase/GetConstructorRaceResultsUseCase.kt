package com.hugo.standings.domain.usecase

import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConstructorRaceResultsUseCase @Inject constructor(
    private val repository: IF1StandingsRepository
) {
    operator fun invoke(season: String, constructorId: String): Flow<Resource<List<ConstructorRaceResultsInfo>>> =
        repository.getConstructorRaceResults(season, constructorId)
}