package com.hugo.standings.data.repository

import com.hugo.datasource.local.LocalDataSource
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.standings.data.mapper.toConstructorInfoList
import com.hugo.standings.data.mapper.toConstructorQualifyingResultInfoList
import com.hugo.standings.data.mapper.toConstructorRaceResultInfoList
import com.hugo.standings.data.mapper.toDriverQualifyingResultInfoList
import com.hugo.standings.data.mapper.toDriverRaceResultInfoList
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

    override fun getConstructorRaceResults(season: String, constructorId: String): Flow<Resource<List<ConstructorRaceResultsInfo>>> = flow {
        AppLogger.d(message = "inside getConstructorRaceResults")
        emit(Resource.Loading())

        try {
            val constructorRaceListFromDB = getConstructorRaceListFromDB()

            constructorRaceListFromDB?.also{
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting constructor Race from DB with size ${it.size}")
            }

            val constructorRaceResults = f1StandingsApi
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
        catch (e:HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }

    }

    override fun getConstructorQualifyingResults(season: String, constructorId: String): Flow<Resource<List<ConstructorQualifyingResultsInfo>>> = flow {
        AppLogger.d(message = "inside getConstructorQualifyingResults")
        emit(Resource.Loading())

        try {
            val constructorQualifyingListFromDB = getConstructorQualifyingListFromDB()

            constructorQualifyingListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting constructor Qualifying Results from DB with size ${it.size}")
            }

            val constructorQualifyingResults = f1StandingsApi
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
        catch (e:HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    override fun getF1ConstructorDetails(constructorId: String): Flow<Resource<ConstructorDetails?>> = flow {
        AppLogger.d(message = "inside getF1CircuitDetails")
        emit(Resource.Loading())

        try {
            val constructorDetailsFromDB = getConstructorDetailsFromDB()

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

    override fun getDriverRaceResults(season: String, driverId: String): Flow<Resource<List<DriverRaceResultsInfo>>> = flow {
        AppLogger.d(message = "inside getDriverRaceResults")
        emit(Resource.Loading())

        try {
            val driverRaceListFromDB = getDriverRaceResultsListFromDB()
            driverRaceListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting Driver Race Results from DB with size ${it.size}")

            }

            val driverRaceResults = f1StandingsApi
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
            val driverQualifyingListFromDB = getDriverQualifyingResultsListFromDB()
            driverQualifyingListFromDB?.also {
                emit(Resource.Success(it))
                AppLogger.d(message = "Success getting Driver Qualifying Results from DB with size ${it.size}")
            }

            val driverQualifyingResults = f1StandingsApi.getDriverQualifyingResult(season = season, driverId = driverId).toDriverQualifyingResultInfoList()

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

    override fun getF1DriverDetails(driverId: String): Flow<Resource<DriverDetails?>> = flow {
        AppLogger.d(message = "inside getF1DriverDetails")
        emit(Resource.Loading())

        try {
            val driverDetailsFromDB = getDriverDetailsFromDB()

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

    private suspend fun insertConstructorQualifyingListInDB(constructorQualifyingList: List<ConstructorQualifyingResultsInfo>) {
        withContext(Dispatchers.IO){
            localDataSource.insertConstructorQualifyingListInDB(constructorQualifyingList)
            AppLogger.d(message = "Success insertConstructorQualifyingListInDB with size ${constructorQualifyingList.size}")
        }
    }

    private suspend fun getConstructorQualifyingListFromDB(): List<ConstructorQualifyingResultsInfo>? {
        return withContext(Dispatchers.IO){
            val constructorQualifyingList = localDataSource.getConstructorQualifyingListFromDB()
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

    private suspend fun getConstructorRaceListFromDB(): List<ConstructorRaceResultsInfo>? {
        return withContext(Dispatchers.IO){
            val constructorRaceList = localDataSource.getConstructorRaceListFromDB()
            if(constructorRaceList.isEmpty()){
                null
            } else{
                constructorRaceList
            }
        }
    }

    private suspend fun insertConstructorDetailsInDB(constructorDetails: ConstructorDetails) {
        withContext(Dispatchers.IO){
            localDataSource.insertConstructorDetailsInDB(constructorDetails)
            AppLogger.d(message = "Success insertConstructorDetailsInDB for constructorId: ${constructorDetails.constructorId}")
        }
    }

    private suspend fun getConstructorDetailsFromDB(): ConstructorDetails? {
        return withContext(Dispatchers.IO) {
            localDataSource.getConstructorDetailsFromDB()
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

    private suspend fun insertDriverQualifyingResultsListInDB(driverQualifyingResultsInfo: List<DriverQualifyingResultsInfo>) {
        withContext(Dispatchers.IO){
            localDataSource.insertDriverQualifyingListInDB(driverQualifyingResultsInfo)
            AppLogger.d(message = "Success insertDriverQualifyingResultsListInDB with size ${driverQualifyingResultsInfo.size}")
        }
    }

    private suspend fun getDriverQualifyingResultsListFromDB(): List<DriverQualifyingResultsInfo>? {
        return withContext(Dispatchers.IO){
            val driverQualifyingResultsInfo = localDataSource.getDriverQualifyingListFromDB()
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

    private suspend fun getDriverRaceResultsListFromDB(): List<DriverRaceResultsInfo>? {
        return withContext(Dispatchers.IO){
            val driverRaceResultsInfo = localDataSource.getDriverRaceListFromDB()
            if(driverRaceResultsInfo.isEmpty()){
                null
            } else{
                driverRaceResultsInfo
            }
        }
    }

    private suspend fun insertDriverDetailsInDB(driverDetails: DriverDetails) {
        withContext(Dispatchers.IO){
            localDataSource.insertDriverDetailsInDB(driverDetails)
            AppLogger.d(message = "Success insertDriverDetailsInDB for driverId: ${driverDetails.driverId}")
        }
    }

    private suspend fun getDriverDetailsFromDB(): DriverDetails? {
        return withContext(Dispatchers.IO) {
            localDataSource.getDriverDetailsFromDB()
        }
    }


}