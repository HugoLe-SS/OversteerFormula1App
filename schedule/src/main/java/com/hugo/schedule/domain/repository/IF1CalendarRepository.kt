package com.hugo.schedule.domain.repository

import com.hugo.schedule.domain.model.F1CalendarInfo
import com.hugo.schedule.domain.model.F1CalendarResult
import com.hugo.schedule.domain.model.F1CircuitInfo
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1CalendarRepository {

    fun getF1Calendar(season: String): Flow<Resource<List<F1CalendarInfo>>>

    fun getF1CalendarResult(season: String, round: String): Flow<Resource<List<F1CalendarResult>>>

    //Supabase
    fun getF1CircuitDetails(circuitId: String): Flow<Resource<F1CircuitInfo?>>
}