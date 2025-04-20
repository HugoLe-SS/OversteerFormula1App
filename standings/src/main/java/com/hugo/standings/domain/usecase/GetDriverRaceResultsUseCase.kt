package com.hugo.standings.domain.usecase

import com.hugo.standings.domain.model.DriverRaceResultInfo
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDriverRaceResultsUseCase @Inject constructor(
    private val repository: IF1StandingsRepository
) {
    operator fun invoke(season: String, driverId: String): Flow<Resource<List<DriverRaceResultInfo>>> =
        repository.getDriverRaceResults(season, driverId)
}