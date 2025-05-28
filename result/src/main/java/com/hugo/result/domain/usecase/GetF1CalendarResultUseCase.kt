package com.hugo.result.domain.usecase

import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.result.domain.repository.IF1ResultRepository
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetF1CalendarResultUseCase @Inject constructor(
    private val repository: IF1ResultRepository
) {
    operator fun invoke(season: String, round: String): Flow<Resource<List<F1CalendarRaceResult>>> = repository.getF1CalendarResult(season, round)
}