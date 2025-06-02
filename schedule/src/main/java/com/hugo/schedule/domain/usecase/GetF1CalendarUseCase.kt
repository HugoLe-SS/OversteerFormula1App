package com.hugo.schedule.domain.usecase

import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.schedule.domain.repository.IF1CalendarRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetF1CalendarUseCase @Inject constructor(
    private val repository: IF1CalendarRepository
) {
    operator fun invoke(season: String): Flow<Resource<List<F1CalendarInfo>, AppError>> = repository.getF1Calendar(season)
}