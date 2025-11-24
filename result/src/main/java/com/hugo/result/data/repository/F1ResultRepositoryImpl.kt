package com.hugo.result.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hugo.datasource.local.RoomDB.datasource.ConstructorQuali.ConstructorQualifyingLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.ConstructorRace.ConstructorRaceLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.DriverQuali.DriverQualifyingLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.DriverRace.DriverRaceLocalDataSource
import com.hugo.datasource.local.RoomDB.datasource.F1CalendarResult.F1CalendarResultLocalDataSource
import com.hugo.datasource.local.TransactionProvider
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.result.data.mapper.toConstructorQualifyingResultInfoList
import com.hugo.result.data.mapper.toDriverQualifyingResultInfoList
import com.hugo.result.data.mapper.toF1CalendarResultList
import com.hugo.result.data.paging.ConstructorRaceResultsRemoteMediator
import com.hugo.result.data.paging.DriverRaceResultsRemoteMediator
import com.hugo.result.data.remote.F1ResultApi
import com.hugo.result.domain.repository.IF1ResultRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.AppUtilities.toAppError
import com.hugo.utilities.Resource
import com.hugo.utilities.com.hugo.utilities.AppLaunchManager
import com.hugo.utilities.logging.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class F1ResultRepositoryImpl @Inject constructor(
    private val f1ResultApi: F1ResultApi,
    private val driverRaceLocalDataSource: DriverRaceLocalDataSource,
    private val constructorRaceLocalDataSource: ConstructorRaceLocalDataSource,
    private val constructorQualifyingLocalDataSource: ConstructorQualifyingLocalDataSource,
    private val driverQualifyingLocalDataSource: DriverQualifyingLocalDataSource,
    private val f1CalendarResultLocalDataSource: F1CalendarResultLocalDataSource,
    private val transactionProvider: TransactionProvider
): IF1ResultRepository {

    override fun getConstructorQualifyingResults(season: String, constructorId: String)
    : Flow<Resource<List<ConstructorQualifyingResultsInfo>, AppError>> = flow {
        AppLogger.d(message = "inside getConstructorQualifyingResults")
        //emit(Resource.Loading())

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
        } catch (e: HttpException) {
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
        //emit(Resource.Loading())
        try {
            if(!AppLaunchManager.hasFetchedCalendarResults.contains(circuitId)){
                AppLogger.d(message = "Network fetch needed for getting F1 calendar Race Result.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val calenderRaceResult = f1ResultApi
                    .getF1CalendarResults(season = season, circuitId = circuitId)
                    .toF1CalendarResultList()
                if(calenderRaceResult.isNotEmpty()){
                    AppLaunchManager.hasFetchedCalendarResults.add(circuitId)
                    insertCalendarResultListInDB(calenderRaceResult) // add to RoomDB
                    AppLogger.d(message = "Success saving Calendar race results to DB with size ${calenderRaceResult.size}")
                }


                emit(Resource.Success(calenderRaceResult))
                AppLogger.d(message = "Success getting Calendar race results ${calenderRaceResult.size}")
            }
            else{
                val calendarResultListFromDB = getCalendarResultListFromDB(circuitId)

                if(!calendarResultListFromDB.isNullOrEmpty()){
                    emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                    emit(Resource.Success(calendarResultListFromDB))
                    AppLogger.d(message = "Success getting Race Result from DB with size ${calendarResultListFromDB.size}")
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

    @OptIn(ExperimentalPagingApi::class)
    override fun getDriverRaceResults(season: String, driverId: String): Flow<PagingData<DriverRaceResultsInfo>> {

        val pagingSourceFactory = { driverRaceLocalDataSource.getDriverRaceResultsPagingSource(driverId) }

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = DriverRaceResultsRemoteMediator(
                season = season,
                driverId = driverId,
                apiService = f1ResultApi,
                localDataSource = driverRaceLocalDataSource,
                transactionProvider =  transactionProvider
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(Dispatchers.IO)
    }


    override fun getDriverQualifyingResults(season: String, driverId: String)
    : Flow<Resource<List<DriverQualifyingResultsInfo>, AppError>> = flow {
        AppLogger.d(message = "inside getDriverQualifyingResults")
        //emit(Resource.Loading())

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
        } catch (e: HttpException) {
            // Handle HttpException for HTTP errors for Retrofit
            emit(Resource.Error(e.toAppError()))
        } catch (e: Exception) {
            // Handle any other exceptions
            emit(Resource.Error(e.toAppError()))
        }
    }.flowOn(Dispatchers.IO)

    @OptIn(ExperimentalPagingApi::class)
    override fun getConstructorRaceResults(
        season: String,
        constructorId: String
    ): Flow<PagingData<ConstructorRaceResultsInfo>> {
        val pagingSourceFactory = { constructorRaceLocalDataSource.getConstructorRacePagingSource(constructorId) }

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = ConstructorRaceResultsRemoteMediator(
                season = season,
                constructorId = constructorId,
                apiService = f1ResultApi,
                localDataSource = constructorRaceLocalDataSource,
                transactionProvider =  transactionProvider
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(Dispatchers.IO)     }


    // Constructor DB operations
    private suspend fun insertConstructorQualifyingListInDB(constructorQualifyingList: List<ConstructorQualifyingResultsInfo>) {
        withContext(Dispatchers.IO){
            constructorQualifyingLocalDataSource.insertConstructorQualifyingListInDB(constructorQualifyingList)
            AppLogger.d(message = "Success insertConstructorQualifyingListInDB with size ${constructorQualifyingList.size}")
        }
    }

    private suspend fun getConstructorQualifyingListFromDB(constructorId: String): List<ConstructorQualifyingResultsInfo>? {
        return withContext(Dispatchers.IO){
            val constructorQualifyingList =
                constructorQualifyingLocalDataSource.getConstructorQualifyingListFromDB(constructorId)
            constructorQualifyingList.ifEmpty {
                null
            }
        }
    }

    private suspend fun insertConstructorRaceListInDB(constructorRaceList: List<ConstructorRaceResultsInfo>) {
        withContext(Dispatchers.IO){
            constructorRaceLocalDataSource.insertConstructorRaceListInDB(constructorRaceList)
            AppLogger.d(message = "Success insertConstructorRaceListInDB with size ${constructorRaceList.size}")
        }
    }

    // Driver DB operations
    private suspend fun insertDriverQualifyingResultsListInDB(driverQualifyingResultsInfo: List<DriverQualifyingResultsInfo>) {
        withContext(Dispatchers.IO){
            driverQualifyingLocalDataSource.insertDriverQualifyingListInDB(driverQualifyingResultsInfo)
            AppLogger.d(message = "Success insertDriverQualifyingResultsListInDB with size ${driverQualifyingResultsInfo.size}")
        }
    }

    private suspend fun getDriverQualifyingResultsListFromDB(driverId: String): List<DriverQualifyingResultsInfo>? {
        return withContext(Dispatchers.IO){
            val driverQualifyingResultsInfo = driverQualifyingLocalDataSource.getDriverQualifyingListFromDB(driverId)
            driverQualifyingResultsInfo.ifEmpty {
                null
            }
        }
    }

    private suspend fun insertDriverRaceResultsListInDB(driverRaceResultsInfo: List<DriverRaceResultsInfo>) {
        withContext(Dispatchers.IO){
            driverRaceLocalDataSource.insertDriverRaceListInDB(driverRaceResultsInfo)
            AppLogger.d(message = "Success insertDriverRaceResultsListInDB with size ${driverRaceResultsInfo.size}")
        }
    }

//    private suspend fun getDriverRaceResultsListFromDB(driverId: String): List<DriverRaceResultsInfo>? {
//        return withContext(Dispatchers.IO){
//            val driverRaceResultsInfo = localDataSource.getDriverRaceListFromDB(driverId)
//            driverRaceResultsInfo.ifEmpty {
//                null
//            }
//        }
//    }

    //Circuit Result DB operations
    private suspend fun insertCalendarResultListInDB(calendarResult: List<F1CalendarRaceResult>) {
        withContext(Dispatchers.IO){
            f1CalendarResultLocalDataSource.insertF1CalendarResultInDB(calendarResult)
            AppLogger.d(message = "Success insertF1CalendarResultInDB with size ${calendarResult.size}")
        }
    }

    private suspend fun getCalendarResultListFromDB(circuitId: String): List<F1CalendarRaceResult>? {
        return withContext(Dispatchers.IO){
            val calendarResult = f1CalendarResultLocalDataSource.getF1CalendarResultFromDB(circuitId = circuitId)
            calendarResult.ifEmpty {
                null
            }
        }
    }
}
