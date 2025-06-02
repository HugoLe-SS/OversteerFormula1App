package com.hugo.result.data.repository

import com.hugo.datasource.local.LocalDataSource
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.result.data.mapper.toConstructorQualifyingResultInfoList
import com.hugo.result.data.mapper.toConstructorRaceResultInfoList
import com.hugo.result.data.mapper.toDriverQualifyingResultInfoList
import com.hugo.result.data.mapper.toDriverRaceResultInfoList
import com.hugo.result.data.mapper.toF1CalendarResultList
import com.hugo.result.data.remote.F1ResultApi
import com.hugo.result.domain.repository.IF1ResultRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.AppUtilities.toAppError
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class F1ResultRepositoryImpl @Inject constructor(
    private val f1ResultApi: F1ResultApi,
    private val localDataSource: LocalDataSource
): IF1ResultRepository {
    override fun getConstructorRaceResults(season: String, constructorId: String)
    : Flow<Resource<List<ConstructorRaceResultsInfo>, AppError>> = flow {
        AppLogger.d(message = "inside getConstructorRaceResults")
        emit(Resource.Loading())

        try {
            val constructorRaceListFromDB = getConstructorRaceListFromDB(constructorId)

            if(!constructorRaceListFromDB.isNullOrEmpty()){
                emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                emit(Resource.Success(constructorRaceListFromDB))
                AppLogger.d(message = "Success getting Race Result from DB with size ${constructorRaceListFromDB.size}")
            }
            else{
                AppLogger.d(message = "Network fetch needed for getting F1 constructor Race Result.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val constructorRaceResults = f1ResultApi
                    .getConstructorRaceResult(season = season, constructorId = constructorId)
                    .toConstructorRaceResultInfoList()

                insertConstructorRaceListInDB(constructorRaceResults) // add to RoomDB
                AppLogger.d(message = "Success saving constructor race results to DB")

                emit(Resource.Success(constructorRaceResults))
                AppLogger.d(message = "Success getting constructor race results ${constructorRaceResults.size}")

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

    override fun getConstructorQualifyingResults(season: String, constructorId: String)
    : Flow<Resource<List<ConstructorQualifyingResultsInfo>, AppError>> = flow {
        AppLogger.d(message = "inside getConstructorQualifyingResults")
        emit(Resource.Loading())

        try {
            val constructorQualifyingListFromDB = getConstructorQualifyingListFromDB(constructorId)

            if(!constructorQualifyingListFromDB.isNullOrEmpty()){
                emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                emit(Resource.Success(constructorQualifyingListFromDB))
                AppLogger.d(message = "Success getting Qualifying Result from DB with size ${constructorQualifyingListFromDB.size}")
            }
            else{
                AppLogger.d(message = "Network fetch needed for getting F1 constructor Qualifying Result.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val constructorQualifyingResults = f1ResultApi
                    .getConstructorQualifyingResult(season = season, constructorId = constructorId)
                    .toConstructorQualifyingResultInfoList()

                insertConstructorQualifyingListInDB(constructorQualifyingResults) // add to RoomDB
                AppLogger.d(message = "Success saving constructor Qualifying results to DB")

                emit(Resource.Success(constructorQualifyingResults))
                AppLogger.d(message = "Success getting constructor Qualifying results ${constructorQualifyingResults.size}")

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

    override fun getDriverRaceResults(season: String, driverId: String)
    : Flow<Resource<List<DriverRaceResultsInfo>, AppError>> = flow {
        AppLogger.d(message = "inside getDriverRaceResults")
        emit(Resource.Loading())

        try {
            val driverRaceListFromDB = getDriverRaceResultsListFromDB(driverId)

            if(!driverRaceListFromDB.isNullOrEmpty()){
                emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                emit(Resource.Success(driverRaceListFromDB))
                AppLogger.d(message = "Success getting Race Result from DB with size ${driverRaceListFromDB.size}")
            }
            else{
                AppLogger.d(message = "Network fetch needed for getting F1 driver Race Result.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val driverRaceResult = f1ResultApi
                    .getDriverRaceResult(season = season, driverId = driverId)
                    .toDriverRaceResultInfoList()

                insertDriverRaceResultsListInDB(driverRaceResult) // add to RoomDB
                AppLogger.d(message = "Success saving driver Race results to DB")

                emit(Resource.Success(driverRaceResult))
                AppLogger.d(message = "Success getting driver Race results ${driverRaceResult.size}")

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

    override fun getDriverQualifyingResults(season: String, driverId: String)
    : Flow<Resource<List<DriverQualifyingResultsInfo>, AppError>> = flow {
        AppLogger.d(message = "inside getDriverQualifyingResults")
        emit(Resource.Loading())

        try {
            val driverQualifyingListFromDB = getDriverQualifyingResultsListFromDB(driverId)

            if(!driverQualifyingListFromDB.isNullOrEmpty()){
                emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                emit(Resource.Success(driverQualifyingListFromDB))
                AppLogger.d(message = "Success getting  Qualifying result from DB with size ${driverQualifyingListFromDB.size}")
            }
            else{
                AppLogger.d(message = "Network fetch needed for getting F1 driver qualifying Result.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val driverQualifyingResult = f1ResultApi
                    .getDriverQualifyingResult(season = season, driverId = driverId)
                    .toDriverQualifyingResultInfoList()

                insertDriverQualifyingResultsListInDB(driverQualifyingResult) // add to RoomDB
                AppLogger.d(message = "Success saving driver Qualifying results to DB")

                emit(Resource.Success(driverQualifyingResult))
                AppLogger.d(message = "Success getting driver Qualifying results ${driverQualifyingResult.size}")

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

    override fun getF1CalendarResult(season: String, circuitId: String)
    : Flow<Resource<List<F1CalendarRaceResult>, AppError>> = flow {
        AppLogger.d(message = "Inside getF1CalendarResult")
        emit(Resource.Loading())
        try {
            val calendarResultListFromDB = getCalendarResultListFromDB(circuitId)

            if(!calendarResultListFromDB.isNullOrEmpty()){
                emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                emit(Resource.Success(calendarResultListFromDB))
                AppLogger.d(message = "Success getting Race Result from DB with size ${calendarResultListFromDB.size}")
            }
            else{
                AppLogger.d(message = "Network fetch needed for getting F1 calendar Race Result.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val calenderRaceResult = f1ResultApi
                    .getF1CalendarResults(season = season, circuitId = circuitId)
                    .toF1CalendarResultList()

                insertCalendarResultListInDB(calenderRaceResult) // add to RoomDB
                AppLogger.d(message = "Success saving Calendar race results to DB with size ${calenderRaceResult.size}")

                emit(Resource.Success(calenderRaceResult))
                AppLogger.d(message = "Success getting Calendar race results ${calenderRaceResult.size}")

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


    // Constructor DB operations
    private suspend fun insertConstructorQualifyingListInDB(constructorQualifyingList: List<ConstructorQualifyingResultsInfo>) {
        withContext(Dispatchers.IO){
            localDataSource.insertConstructorQualifyingListInDB(constructorQualifyingList)
            AppLogger.d(message = "Success insertConstructorQualifyingListInDB with size ${constructorQualifyingList.size}")
        }
    }

    private suspend fun getConstructorQualifyingListFromDB(constructorId: String): List<ConstructorQualifyingResultsInfo>? {
        return withContext(Dispatchers.IO){
            val constructorQualifyingList = localDataSource.getConstructorQualifyingListFromDB(constructorId)
            if(constructorQualifyingList.isEmpty()){
                null
            } else{
                constructorQualifyingList
            }
        }
    }

    private suspend fun insertConstructorRaceListInDB(constructorRaceList: List<ConstructorRaceResultsInfo>) {
        withContext(Dispatchers.IO){
            localDataSource.insertConstructorRaceListInDB(constructorRaceList)
            AppLogger.d(message = "Success insertConstructorRaceListInDB with size ${constructorRaceList.size}")
        }
    }

    private suspend fun getConstructorRaceListFromDB(constructorId: String): List<ConstructorRaceResultsInfo>? {
        return withContext(Dispatchers.IO){
            val constructorRaceList = localDataSource.getConstructorRaceListFromDB(constructorId)
            if(constructorRaceList.isEmpty()){
                null
            } else{
                constructorRaceList
            }
        }
    }

    // Driver DB operations
    private suspend fun insertDriverQualifyingResultsListInDB(driverQualifyingResultsInfo: List<DriverQualifyingResultsInfo>) {
        withContext(Dispatchers.IO){
            localDataSource.insertDriverQualifyingListInDB(driverQualifyingResultsInfo)
            AppLogger.d(message = "Success insertDriverQualifyingResultsListInDB with size ${driverQualifyingResultsInfo.size}")
        }
    }

    private suspend fun getDriverQualifyingResultsListFromDB(driverId: String): List<DriverQualifyingResultsInfo>? {
        return withContext(Dispatchers.IO){
            val driverQualifyingResultsInfo = localDataSource.getDriverQualifyingListFromDB(driverId)
            if(driverQualifyingResultsInfo.isEmpty()){
                null
            } else{
                driverQualifyingResultsInfo
            }
        }
    }

    private suspend fun insertDriverRaceResultsListInDB(driverRaceResultsInfo: List<DriverRaceResultsInfo>) {
        withContext(Dispatchers.IO){
            localDataSource.insertDriverRaceListInDB(driverRaceResultsInfo)
            AppLogger.d(message = "Success insertDriverRaceResultsListInDB with size ${driverRaceResultsInfo.size}")
        }
    }

    private suspend fun getDriverRaceResultsListFromDB(driverId: String): List<DriverRaceResultsInfo>? {
        return withContext(Dispatchers.IO){
            val driverRaceResultsInfo = localDataSource.getDriverRaceListFromDB(driverId)
            if(driverRaceResultsInfo.isEmpty()){
                null
            } else{
                driverRaceResultsInfo
            }
        }
    }

    //Circuit Result DB operations
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