package com.hugo.standings.domain.usecase

import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDriverQualifyingResultsUseCase @Inject constructor(
    private val repository: IF1StandingsRepository
) {
    operator fun invoke(season: String, driverId: String): Flow<Resource<List<DriverQualifyingResultsInfo>>> =
        repository.getDriverQualifyingResults(season, driverId)
}