package com.hugo.standings.domain.usecase

import com.hugo.standings.domain.model.DriverStandingsInfo
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDriverStandingsUseCase @Inject constructor(
    private val repository: IF1StandingsRepository
) {
    operator fun invoke(season: String): Flow<Resource<List<DriverStandingsInfo>>> = repository.getDriverStandings(season)
}