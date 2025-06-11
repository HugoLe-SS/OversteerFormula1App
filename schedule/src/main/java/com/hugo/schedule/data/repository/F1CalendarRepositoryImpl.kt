package com.hugo.schedule.data.repository

import com.hugo.datasource.local.LocalDataSource
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.schedule.data.mapper.toF1CalendarInfoList
import com.hugo.schedule.data.remote.F1ScheduleApi
import com.hugo.schedule.domain.repository.IF1CalendarRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.AppUtilities.toAppError
import com.hugo.utilities.Resource
import com.hugo.utilities.com.hugo.utilities.AppLaunchManager
import com.hugo.utilities.logging.AppLogger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import javax.inject.Inject

class F1CalendarRepositoryImpl @Inject constructor(
    private val f1ScheduleApi: F1ScheduleApi,
    private val supabaseClient: SupabaseClient,
    private val localDataSource: LocalDataSource
): IF1CalendarRepository {

    override fun getF1Calendar(season: String): Flow<Resource<List<F1CalendarInfo>, AppError>> = flow {
        AppLogger.d(message = "Inside getF1Calendar")

        //emit(Resource.Loading())

        try {
            if(!AppLaunchManager.hasFetchedCalendar){
                AppLogger.d(message = "Network fetch needed for getting F1 calendar.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val f1Calendar = f1ScheduleApi
                    .getF1Calendar(season)
                    .toF1CalendarInfoList()

                if(f1Calendar.isNotEmpty()){
                    AppLaunchManager.hasFetchedCalendar = true
                    insertCalendarInfoListInDB(f1Calendar) // add to RoomDB
                    AppLogger.d(message = "Success saving Calendar Info to DB")
                }

                emit(Resource.Success(f1Calendar))
                AppLogger.d(message = "Success getting Calendar Info with size ${f1Calendar.size}")
            }

            else{
                emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                val calendarInfoListFromDB = getCalendarInfoListFromDB()
                if(!calendarInfoListFromDB.isNullOrEmpty()){
                    emit(Resource.Success(calendarInfoListFromDB))
                    AppLogger.d(message = "Success getting Calendar from DB with size ${calendarInfoListFromDB.size}")
                }
            }


        } catch (e: IOException) {
            // Handle IOException specifically for network issues
            emit(Resource.Error(e.toAppError()))
        } catch (e: retrofit2.HttpException) {
            // Handle HttpException for HTTP errors for Retrofit
            emit(Resource.Error(e.toAppError()))
        } catch (e: Exception) {
            // Handle any other exceptions
            emit(Resource.Error(e.toAppError()))
        }
    }.flowOn(Dispatchers.IO)

    // Supabase
    override fun getF1CircuitDetails(circuitId: String): Flow<Resource<F1CircuitDetails?, AppError>> = flow {
        AppLogger.d(message = "Inside getF1CircuitDetails")
        //emit(Resource.Loading())

        try {
            if (!AppLaunchManager.fetchedCircuitDetails.contains(circuitId)) {
                AppLogger.d(message = "Network fetch needed for getting F1 circuit details.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val result = supabaseClient
                    .postgrest["CircuitDetails"]
                    .select {
                        filter { eq("circuitId", circuitId) }
                    }

                val f1Circuit = result.decodeSingleOrNull<F1CircuitDetails>()

                if (f1Circuit != null) {
                    insertCalendarDetailsInDB(f1Circuit)
                    AppLaunchManager.fetchedCircuitDetails.add(circuitId)
                }

                emit(Resource.Success(f1Circuit))
            } else {
                emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                val circuitDetailsFromDB = getCalendarDetailsFromDB(circuitId)
                emit(Resource.Success(circuitDetailsFromDB))
                AppLogger.d(message = "Success getting driver details from DB")
            }


        } catch (e: IOException) {
            // Handle IOException specifically for network issues
            emit(Resource.Error(e.toAppError()))
        } catch (e: retrofit2.HttpException) {
            // Handle HttpException for HTTP errors for Retrofit
            emit(Resource.Error(e.toAppError()))
        } catch (e: Exception) {
            // Handle any other exceptions
            emit(Resource.Error(e.toAppError()))
        }
    }.flowOn(Dispatchers.IO)

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


}
