package com.hugo.schedule.domain.usecase

import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.schedule.domain.repository.IF1CalendarRepository
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetF1CalendarResultUseCase @Inject constructor(
    private val repository: IF1CalendarRepository
) {
    operator fun invoke(season: String, round: String): Flow<Resource<List<F1CalendarRaceResult>>> = repository.getF1CalendarResult(season, round)
}