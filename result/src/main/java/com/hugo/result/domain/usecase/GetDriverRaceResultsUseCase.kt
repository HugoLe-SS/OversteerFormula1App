package com.hugo.result.domain.usecase

import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.result.domain.repository.IF1ResultRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDriverRaceResultsUseCase @Inject constructor(
    private val repository: IF1ResultRepository
) {
    operator fun invoke(season: String, driverId: String): Flow<Resource<List<DriverRaceResultsInfo>, AppError>> =
        repository.getDriverRaceResults(season, driverId)
}