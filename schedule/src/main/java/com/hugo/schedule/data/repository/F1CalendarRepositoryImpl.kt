package com.hugo.schedule.data.repository

import com.hugo.datasource.local.LocalDataSource
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.schedule.data.mapper.toF1CalendarInfoList
import com.hugo.schedule.data.mapper.toF1CalendarResultList
import com.hugo.schedule.data.remote.F1ScheduleApi
import com.hugo.schedule.domain.repository.IF1CalendarRepository
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class F1CalendarRepositoryImpl @Inject constructor(
    private val f1ScheduleApi: F1ScheduleApi,
    private val supabaseClient: SupabaseClient,
    private val localDataSource: LocalDataSource
): IF1CalendarRepository {

    override fun getF1Calendar(season: String): Flow<Resource<List<F1CalendarInfo>>> = flow {
        AppLogger.d(message = "Inside getF1Calendar")

        emit(Resource.Loading())
        try {
            val calendarInfoListFromDB = getCalendarInfoListFromDB()
            calendarInfoListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting F1 Calendar from DB with size ${it.size}")
            }

            val f1Calendar = f1ScheduleApi
                .getF1Calendar(season)
                .toF1CalendarInfoList()
            if(f1Calendar != calendarInfoListFromDB){
                emit(Resource.Success(f1Calendar))
                AppLogger.d(message = "Success getting F1 Calendar ${f1Calendar.size}")
                insertCalendarInfoListInDB(f1Calendar) // add to RoomDB
            }
            else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }


        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting F1 Calendar: ${e.localizedMessage}")
        } catch (e: HttpException) {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    override fun getF1CalendarResult(
        season: String,
        circuitId: String
    ): Flow<Resource<List<F1CalendarRaceResult>>> = flow {
        AppLogger.d(message = "Inside getF1CalendarResult")
        emit(Resource.Loading())
        try {
            val calendarResultListFromDB = getCalendarResultListFromDB(circuitId)
            calendarResultListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting F1 Calendar result from DB with size ${it.size}")
            }

            val f1CalendarResult = f1ScheduleApi
                .getF1CalendarResults(season = season, circuitId = circuitId)
                .toF1CalendarResultList()
            if(f1CalendarResult != calendarResultListFromDB){
                emit(Resource.Success(f1CalendarResult))
                AppLogger.d(message = "Success getting F1 Calendar result ${f1CalendarResult.size}")
                insertCalendarResultListInDB(f1CalendarResult) // add to RoomDB
            }else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }


        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting F1 Calendar result: ${e.localizedMessage}")
        } catch (e: HttpException) {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    // Supabase
    override fun getF1CircuitDetails(circuitId: String): Flow<Resource<F1CircuitDetails?>> = flow {
        AppLogger.d(message = "Inside getF1CircuitDetails")
        emit(Resource.Loading())
        try {
            val circuitDetailsFromDB = getCalendarDetailsFromDB(circuitId)
            circuitDetailsFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting F1 Circuit details from DB with circuitID: ${it.circuitId}")
            }

            val result = supabaseClient
                .postgrest["CircuitDetails"]
                .select {
                    filter{
                        eq("circuitId", circuitId)
                    }
                }

            val f1Circuit = result.decodeSingleOrNull<F1CircuitDetails>()

            if(f1Circuit != circuitDetailsFromDB){
                emit(Resource.Success(f1Circuit))
                AppLogger.d(message = "Success getting F1 Circuit Info ${f1Circuit?.circuitId}")
                insertCalendarDetailsInDB(f1Circuit!!) // add to RoomDB
            }
            else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting F1 Circuit info: ${e.localizedMessage}")
        } catch (e: HttpException) {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    //Calendar Schedule Info
    private suspend fun insertCalendarInfoListInDB(calendarInfo: List<F1CalendarInfo>) {
        withContext(Dispatchers.IO){
            localDataSource.insertF1CalendarInDB(calendarInfo)
            AppLogger.d(message = "Success insertCalendarInfoListInDB with size ${calendarInfo.size}")
        }
    }

    private suspend fun getCalendarInfoListFromDB(): List<F1CalendarInfo>? {
        return withContext(Dispatchers.IO){
            val calendarInfoList = localDataSource.getF1CalendarFromDB()
            if(calendarInfoList.isEmpty()){
                null
            } else{
                calendarInfoList
            }
        }
    }

    private suspend fun insertCalendarDetailsInDB(calendarDetails: F1CircuitDetails) {
        withContext(Dispatchers.IO){
            localDataSource.insertF1CircuitDetailsInDB(calendarDetails)
            AppLogger.d(message = "Success insertCalendarInfoListInDB with circuitID:  ${calendarDetails.circuitId}")
        }
    }

    private suspend fun getCalendarDetailsFromDB(circuitId: String): F1CircuitDetails? {
        return withContext(Dispatchers.IO){
            localDataSource.getF1CircuitDetailsFromDB(circuitId)
        }
    }

    private suspend fun insertCalendarResultListInDB(calendarResult: List<F1CalendarRaceResult>) {
        withContext(Dispatchers.IO){
            localDataSource.insertF1CalendarResultInDB(calendarResult)
            AppLogger.d(message = "Success insertF1CalendarResultInDB with size ${calendarResult.size}")
        }
    }

    private suspend fun getCalendarResultListFromDB(circuitId: String): List<F1CalendarRaceResult>? {
        return withContext(Dispatchers.IO){
            val calendarResult = localDataSource.getF1CalendarResultFromDB(circuitId = circuitId)
            if(calendarResult.isEmpty()){
                null
            } else{
                calendarResult
            }
        }
    }


}
