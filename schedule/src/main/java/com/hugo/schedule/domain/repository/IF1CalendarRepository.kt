package com.hugo.schedule.domain.repository

import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.utilities.AppError
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1CalendarRepository {

    fun getF1Calendar(season: String): Flow<Resource<List<F1CalendarInfo>, AppError>>

    //Supabase
    fun getF1CircuitDetails(circuitId: String): Flow<Resource<F1CircuitDetails?, AppError>>
}