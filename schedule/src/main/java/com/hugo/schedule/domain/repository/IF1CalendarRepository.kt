package com.hugo.schedule.domain.repository

import com.hugo.schedule.domain.model.F1CalendarInfo
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1CalendarRepository {

    fun getF1Calendar(season: String): Flow<Resource<List<F1CalendarInfo>>>

}