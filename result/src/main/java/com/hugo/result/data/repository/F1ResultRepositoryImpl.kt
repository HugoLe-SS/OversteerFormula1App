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
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class F1ResultRepositoryImpl @Inject constructor(
    private val f1ResultApi: F1ResultApi,
    private val localDataSource: LocalDataSource
): IF1ResultRepository {
    override fun getConstructorRaceResults(season: String, constructorId: String): Flow<Resource<List<ConstructorRaceResultsInfo>>> = flow {
        AppLogger.d(message = "inside getConstructorRaceResults")
        emit(Resource.Loading())

        try {
            val constructorRaceListFromDB = getConstructorRaceListFromDB(constructorId)

            constructorRaceListFromDB?.also{
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting constructor Race from DB ${constructorId}")
            }

            val constructorRaceResults = f1ResultApi
                .getConstructorRaceResult(season = season, constructorId = constructorId)
                .toConstructorRaceResultInfoList()

            if(constructorRaceResults != constructorRaceListFromDB){
                emit(Resource.Success(constructorRaceResults))
                AppLogger.d(message = "Success getting constructor race results ${constructorRaceResults.size}")
                insertConstructorRaceListInDB(constructorRaceResults) // add to RoomDB
            }
            else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting constructor race results: ${e.localizedMessage}")
        }
        catch (e: HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }

    }

    override fun getConstructorQualifyingResults(season: String, constructorId: String): Flow<Resource<List<ConstructorQualifyingResultsInfo>>> = flow {
        AppLogger.d(message = "inside getConstructorQualifyingResults")
        emit(Resource.Loading())

        try {
            val constructorQualifyingListFromDB = getConstructorQualifyingListFromDB(constructorId)

            constructorQualifyingListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting constructor Qualifying Results from DB ${constructorId}")
            }

            val constructorQualifyingResults = f1ResultApi
                .getConstructorQualifyingResult(season = season, constructorId = constructorId)
                .toConstructorQualifyingResultInfoList()

            if (constructorQualifyingResults != constructorQualifyingListFromDB) {
                emit(Resource.Success(constructorQualifyingResults)) // Emit updated data
                AppLogger.d(message = "Success getting Constructor qualifying results ${constructorQualifyingResults.size}")
                insertConstructorQualifyingListInDB(constructorQualifyingResults) // Update DB
            } else {
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }



        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting Constructor qualifying results: ${e.localizedMessage}")
        }
        catch (e: HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    override fun getDriverRaceResults(season: String, driverId: String): Flow<Resource<List<DriverRaceResultsInfo>>> = flow {
        AppLogger.d(message = "inside getDriverRaceResults")
        emit(Resource.Loading())

        try {
            val driverRaceListFromDB = getDriverRaceResultsListFromDB(driverId)
            driverRaceListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting Driver Race Results from DB ${driverId}")

            }

            val driverRaceResults = f1ResultApi
                .getDriverRaceResult(season = season, driverId = driverId)
                .toDriverRaceResultInfoList()

            if(driverRaceResults != driverRaceListFromDB)
            {
                emit(Resource.Success(driverRaceResults))
                AppLogger.d(message = "Success getting driver race results ${driverRaceResults.size}")
                insertDriverRaceResultsListInDB(driverRaceResults) // Update DB
            }
            else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }


        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting driver race results: ${e.localizedMessage}")
        }
        catch (e:HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }

    }

    override fun getDriverQualifyingResults(season: String, driverId: String): Flow<Resource<List<DriverQualifyingResultsInfo>>> = flow {
        AppLogger.d(message = "inside getDriverQualifyingResults")
        emit(Resource.Loading())

        try {
            val driverQualifyingListFromDB = getDriverQualifyingResultsListFromDB(driverId)
            driverQualifyingListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting Driver Qualifying Results from DB ${driverId}")
            }

            val driverQualifyingResults = f1ResultApi.getDriverQualifyingResult(season = season, driverId = driverId).toDriverQualifyingResultInfoList()

            if(driverQualifyingResults != driverQualifyingListFromDB)
            {
                emit(Resource.Success(driverQualifyingResults))
                AppLogger.d(message = "Success getting driver qualifying results ${driverQualifyingResults.size}")
                insertDriverQualifyingResultsListInDB(driverQualifyingResults)
            }
            else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting driver qualifying results: ${e.localizedMessage}")
        }
        catch (e:HttpException)
        {
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

            val f1CalendarResult = f1ResultApi
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