package com.hugo.result.domain.usecase

import androidx.paging.PagingData
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.result.domain.repository.IF1ResultRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDriverRaceResultsUseCase @Inject constructor(
    private val repository: IF1ResultRepository
) {
    operator fun invoke(season: String, driverId: String): Flow<PagingData<DriverRaceResultsInfo>> =
        repository.getDriverRaceResults(season, driverId)
}