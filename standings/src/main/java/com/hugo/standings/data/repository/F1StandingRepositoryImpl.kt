package com.hugo.standings.data.repository

import com.hugo.datasource.local.LocalDataSource
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.standings.data.mapper.toConstructorInfoList
import com.hugo.standings.data.mapper.toDriverStandingsInfoList
import com.hugo.standings.data.remote.F1StandingsApi
import com.hugo.standings.domain.repository.IF1StandingsRepository
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


class F1StandingRepositoryImpl @Inject constructor(
    private val f1StandingsApi: F1StandingsApi,
    private val supabaseClient: SupabaseClient,
    private val localDataSource: LocalDataSource

): IF1StandingsRepository {

    override fun getConstructorStandings(season: String): Flow<Resource<List<ConstructorStandingsInfo>, AppError>> =
        flow {

            AppLogger.d(message = "Inside getConstructorStandings")
            try {

                if (!AppLaunchManager.hasFetchedConstructorStandings) {
                    AppLogger.d(message = "Network fetch needed for constructor standings.")
                    emit(Resource.Loading(isFetchingFromNetwork = true)) // network fetch so that it can show loading indicator

                    val constructorStandings = f1StandingsApi
                        .getConstructorStandings(season)
                        .toConstructorInfoList()


                    if (constructorStandings.isNotEmpty()) {
                        AppLaunchManager.hasFetchedConstructorStandings = true
                        insertConstructorStandingsListInDB(constructorStandings) // add to RoomDB
                        AppLogger.d(message = "Success saving constructor standings to DB")
                    }

                    emit(Resource.Success(constructorStandings))
                    AppLogger.d(message = "Success getting constructor standings ${constructorStandings.size}")
                } else {
                    emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator
                    val constructorStandingsListFromDB = getConstructorStandingsListFromDB()
                    if (!constructorStandingsListFromDB.isNullOrEmpty()) {
                        emit(Resource.Success(constructorStandingsListFromDB))
                        AppLogger.d(message = "Success getting constructor standings from DB with size ${constructorStandingsListFromDB.size}")
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

    override fun getDriverStandings(season: String): Flow<Resource<List<DriverStandingsInfo>, AppError>> =
        flow {
            AppLogger.d(message = "Inside getDriverStandings")

            try {
                if (!AppLaunchManager.hasFetchedDriverStandings) {
                    AppLogger.d(message = "Network fetch needed for driver standings.")
                    emit(Resource.Loading(isFetchingFromNetwork = true)) // network fetch so that it can show loading indicator

                    val driverStandings = f1StandingsApi
                        .getDriverStandings(season)
                        .toDriverStandingsInfoList()

                    if (driverStandings.isNotEmpty()) {
                        insertDriverStandingsListInDB(driverStandings) // add to RoomDB
                        AppLogger.d(message = "Success saving driver standings to DB")
                        AppLaunchManager.hasFetchedDriverStandings = true
                    }

                    emit(Resource.Success(driverStandings))
                    AppLogger.d(message = "Success getting driver standings ${driverStandings.size}")
                }

                else {
                    emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                    val driverStandingsListFromDB = getDriverStandingsListFromDB()
                    if (!driverStandingsListFromDB.isNullOrEmpty()) {
                        emit(Resource.Success(driverStandingsListFromDB))
                        AppLogger.d(message = "Success getting driver standings from DB with size ${driverStandingsListFromDB.size}")
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

    override fun getF1ConstructorDetails(constructorId: String): Flow<Resource<ConstructorDetails?, AppError>> =
        flow{
            AppLogger.d(message = "inside getF1ConstructorDetail")
            //emit(Resource.Loading())
            try {

                if (!AppLaunchManager.fetchedConstructorDetails.contains(constructorId)) {
                    AppLogger.d(message = "Network fetch needed for constructor Details.")
                    emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                    val result = supabaseClient
                        .postgrest["ConstructorDetails"]
                        .select {
                            filter {
                                eq("constructorId", constructorId)
                            }
                        }

                    val constructorDetails = result.decodeSingleOrNull<ConstructorDetails>()

                    if (constructorDetails != null) {
                        insertConstructorDetailsInDB(constructorDetails) // add to RoomDB
                        AppLogger.d(message = "Success getting F1 constructor details ${constructorDetails.constructorId}")
                        AppLaunchManager.fetchedConstructorDetails.add(constructorId)
                    }

                    emit(Resource.Success(constructorDetails))
                } else {
                    emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                    val constructorDetailsFromDB = getConstructorDetailsFromDB(constructorId)
                    emit(Resource.Success(constructorDetailsFromDB))
                    AppLogger.d(message = "Success getting constructor details from DB")
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

    override fun getF1DriverDetails(driverId: String): Flow<Resource<DriverDetails?, AppError>> =
        flow {
            AppLogger.d(message = "inside getF1DriverDetails")
            //emit(Resource.Loading())

            try {
                if (!AppLaunchManager.fetchedDriverDetails.contains(driverId)) {
                    AppLogger.d(message = "Network fetch needed for driver details.")
                    emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                    val result = supabaseClient
                        .postgrest["DriverDetails"]
                        .select {
                            filter {
                                eq("driverId", driverId)
                            }
                        }

                    val driverDetails = result.decodeSingleOrNull<DriverDetails>()

                    if (driverDetails != null) {
                        AppLogger.d(message = "Success getting F1 driver details ${driverDetails.driverId}")
                        insertDriverDetailsInDB(driverDetails) // add to RoomDB
                        AppLaunchManager.fetchedDriverDetails.add(driverId)
                    }

                    emit(Resource.Success(driverDetails))

                } else {
                    emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                    val driverDetailsFromDB = getDriverDetailsFromDB(driverId)
                    emit(Resource.Success(driverDetailsFromDB))
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

    //Constructor
    private suspend fun insertConstructorStandingsListInDB(constructorStandingsList: List<ConstructorStandingsInfo>) {
        withContext(Dispatchers.IO){
            localDataSource.insertConstructorStandingsListInDB(constructorStandingsList)
            AppLogger.d(message = "Success insertConstructorStandingsListInDB with size ${constructorStandingsList.size}")
        }
    }

    private suspend fun getConstructorStandingsListFromDB(): List<ConstructorStandingsInfo>? {
        return withContext(Dispatchers.IO){
            val constructorStandingsList = localDataSource.getConstructorStandingsListFromDB()
            if(constructorStandingsList.isEmpty()){
                null
            } else{
                constructorStandingsList
            }
        }
    }

    private suspend fun insertConstructorDetailsInDB(constructorDetails: ConstructorDetails) {
        withContext(Dispatchers.IO){
            localDataSource.insertConstructorDetailsInDB(constructorDetails)
            AppLogger.d(message = "Success insertConstructorDetailsInDB for constructorId: ${constructorDetails.constructorId}")
        }
    }

    private suspend fun getConstructorDetailsFromDB(constructorId: String): ConstructorDetails? {
        return withContext(Dispatchers.IO) {
            localDataSource.getConstructorDetailsFromDB(constructorId)
        }
    }

    //Driver
    private suspend fun insertDriverStandingsListInDB(driverStandingsList: List<DriverStandingsInfo>) {
        withContext(Dispatchers.IO){
            localDataSource.insertDriverStandingsListInDB(driverStandingsList)
            AppLogger.d(message = "Success insertDriverStandingsListInDB with size ${driverStandingsList.size}")
        }
    }

    private suspend fun getDriverStandingsListFromDB(): List<DriverStandingsInfo>? {
        return withContext(Dispatchers.IO){
            val driverStandingsList = localDataSource.getDriverStandingsListFromDB()
            if(driverStandingsList.isEmpty()){
                null
            } else{
                driverStandingsList
            }
        }
    }

    private suspend fun insertDriverDetailsInDB(driverDetails: DriverDetails) {
        withContext(Dispatchers.IO){
            localDataSource.insertDriverDetailsInDB(driverDetails)
            AppLogger.d(message = "Success insertDriverDetailsInDB for driverId: ${driverDetails.driverId}")
        }
    }

    private suspend fun getDriverDetailsFromDB(driverId: String): DriverDetails? {
        return withContext(Dispatchers.IO) {
            localDataSource.getDriverDetailsFromDB(driverId)
        }
    }

}