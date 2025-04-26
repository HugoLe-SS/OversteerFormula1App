package com.hugo.schedule.data.repository

import com.hugo.schedule.data.mapper.toF1CalendarInfoList
import com.hugo.schedule.data.mapper.toF1CalendarResultList
import com.hugo.schedule.data.remote.F1ScheduleApi
import com.hugo.schedule.domain.model.F1CalendarInfo
import com.hugo.schedule.domain.model.F1CalendarResult
import com.hugo.schedule.domain.repository.IF1CalendarRepository
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class F1CalendarRepositoryImpl @Inject constructor(
    private val f1ScheduleApi: F1ScheduleApi
): IF1CalendarRepository {

    override fun getF1Calendar(season: String): Flow<Resource<List<F1CalendarInfo>>> = flow {
        AppLogger.d(message="Inside getF1Calendar")
        emit(Resource.Loading())
        try {
            val f1Calendar = f1ScheduleApi.getF1Calendar(season)
            emit(Resource.Success(f1Calendar.toF1CalendarInfoList()))
            AppLogger.d(message = "Success getting F1 Calendar ${f1Calendar.toF1CalendarInfoList().size}")

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting F1 Calendar: ${e.localizedMessage}")
        }
        catch (e: HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    override fun getF1CalendarResult(season: String, round: String): Flow<Resource<List<F1CalendarResult>>> = flow {
        AppLogger.d(message="Inside getF1CalendarResult")
        emit(Resource.Loading())
        try {
            val f1Calendar = f1ScheduleApi.getF1CalendarResults(season = season, round = round)
            emit(Resource.Success(f1Calendar.toF1CalendarResultList()))
            AppLogger.d(message = "Success getting F1 Calendar result ${f1Calendar.toF1CalendarResultList().size}")

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting F1 Calendar result: ${e.localizedMessage}")
        }
        catch (e: HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

}