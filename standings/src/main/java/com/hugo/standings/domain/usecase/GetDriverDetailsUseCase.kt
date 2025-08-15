package com.hugo.standings.domain.usecase

import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDriverDetailsUseCase @Inject constructor(
    private val repository: IF1StandingsRepository
){
    operator fun invoke(driverId: String): Flow<Resource<DriverDetails?, AppError>> = repository.getF1DriverDetails(driverId)
}
