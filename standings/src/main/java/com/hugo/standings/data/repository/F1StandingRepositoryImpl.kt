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


class F1StandingRepositoryImpl @Inject constructor(
    private val f1StandingsApi: F1StandingsApi,
    private val supabaseClient: SupabaseClient,
    private val localDataSource: LocalDataSource

): IF1StandingsRepository {
    override fun getConstructorStandings(season: String): Flow<Resource<List<ConstructorStandingsInfo>>> = flow {



        AppLogger.d(message="Inside getConstructorStandings")
        emit(Resource.Loading())
        try {
            val constructorStandingsListFromDB = getConstructorStandingsListFromDB()
            constructorStandingsListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting constructor standings from DB with size ${it.size}")
            }

            val constructorStandings = f1StandingsApi
                .getConstructorStandings(season)
                .toConstructorInfoList()

            if(constructorStandings != constructorStandingsListFromDB){
                emit(Resource.Success(constructorStandings))
                AppLogger.d(message = "Success getting constructor standings ${constructorStandings.size}")
                insertConstructorStandingsListInDB(constructorStandings) // add to RoomDB
            }
            else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting Constructor standings: ${e.localizedMessage}")
        }
        catch (e:HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    override fun getF1ConstructorDetails(constructorId: String): Flow<Resource<ConstructorDetails?>> = flow {
        AppLogger.d(message = "inside getF1ConstructorDetail")
        emit(Resource.Loading())

        try {
            val constructorDetailsFromDB = getConstructorDetailsFromDB(constructorId)

            constructorDetailsFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting constructor details from DB ${it.constructorId}")
            }

            val result = supabaseClient
                .postgrest["ConstructorDetails"]
                .select {
                    filter{
                        eq("constructorId", constructorId)
                    }
                }

            val constructorDetails = result.decodeSingleOrNull<ConstructorDetails>()

            if(constructorDetails != constructorDetailsFromDB){
                AppLogger.d(message = "Success getting F1 constructor details ${constructorDetails?.constructorId}")
                emit(Resource.Success(constructorDetails))
                insertConstructorDetailsInDB(constructorDetails!!) // add to RoomDB
            }
            else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting F1 constructor details: ${e.localizedMessage}")
        } catch (e: HttpException) {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }

    }

    override fun getDriverStandings(season: String): Flow<Resource<List<DriverStandingsInfo>>> = flow {
        AppLogger.d(message = "Inside getDriverStandings")
        emit(Resource.Loading())
        try {
            val driverStandingsListFromDB = getDriverStandingsListFromDB()
            driverStandingsListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting driver standings from DB with size ${it.size}")
            }

            val driverStandings = f1StandingsApi.getDriverStandings(season).toDriverStandingsInfoList()

            if(driverStandings != driverStandingsListFromDB)
            {
                emit(Resource.Success(driverStandings))
                AppLogger.d(message = "Success getting driver standings ${driverStandings.size}")
                insertDriverStandingsListInDB(driverStandings) // add to RoomDB
            }
            else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }


        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting Constructor standings: ${e.localizedMessage}")
        }
        catch (e:HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    override fun getF1DriverDetails(driverId: String): Flow<Resource<DriverDetails?>> = flow {
        AppLogger.d(message = "inside getF1DriverDetails")
        emit(Resource.Loading())

        try {
            val driverDetailsFromDB = getDriverDetailsFromDB(driverId)

            driverDetailsFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting driver details from DB ${it.driverId}")
            }

            val result = supabaseClient
                .postgrest["DriverDetails"]
                .select {
                    filter{
                        eq("driverId", driverId)
                    }
                }

            val driverDetails = result.decodeSingleOrNull<DriverDetails>()

            if(driverDetails != driverDetailsFromDB){
                AppLogger.d(message = "Success getting F1 driver details ${driverDetails?.driverId}")
                emit(Resource.Success(driverDetails))
                insertDriverDetailsInDB(driverDetails!!) // add to RoomDB
            }
            else{
                AppLogger.d(message = "API data and DB data are identical; skipping update")
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting F1 Driver details: ${e.localizedMessage}")
        } catch (e: HttpException) {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }

    }


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