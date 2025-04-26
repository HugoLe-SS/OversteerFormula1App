package com.hugo.schedule.domain.usecase

import com.hugo.schedule.domain.model.F1CalendarInfo
import com.hugo.schedule.domain.repository.IF1CalendarRepository
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetF1CalendarUseCase @Inject constructor(
    private val repository: IF1CalendarRepository
) {
    operator fun invoke(season: String): Flow<Resource<List<F1CalendarInfo>>> = repository.getF1Calendar(season)
}