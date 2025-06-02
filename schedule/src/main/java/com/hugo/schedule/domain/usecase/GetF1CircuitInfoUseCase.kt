package com.hugo.schedule.domain.usecase

import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.schedule.domain.repository.IF1CalendarRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetF1CircuitInfoUseCase @Inject constructor(
    private val repository: IF1CalendarRepository
) {
    operator fun invoke(circuitId: String):Flow<Resource<F1CircuitDetails?, AppError>> = repository.getF1CircuitDetails(circuitId)
}