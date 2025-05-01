package com.hugo.standings.domain.usecase

import com.hugo.standings.domain.model.DriverDetails
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDriverDetailsUseCase @Inject constructor(
    private val repository: IF1StandingsRepository
){
    operator fun invoke(driverId: String): Flow<Resource<DriverDetails?>> = repository.getF1DriverDetails(driverId)
}
