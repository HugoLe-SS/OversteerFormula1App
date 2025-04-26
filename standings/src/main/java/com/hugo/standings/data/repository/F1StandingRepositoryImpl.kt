package com.hugo.standings.data.repository

import com.hugo.standings.data.mapper.toConstructorInfoList
import com.hugo.standings.data.mapper.toConstructorQualifyingResultInfoList
import com.hugo.standings.data.mapper.toConstructorRaceResultInfoList
import com.hugo.standings.data.mapper.toDriverQualifyingResultInfoList
import com.hugo.standings.data.mapper.toDriverRaceResultInfoList
import com.hugo.standings.data.mapper.toDriverStandingsInfoList
import com.hugo.standings.data.remote.F1StandingsApi
import com.hugo.standings.domain.model.ConstructorQualifyingResultsInfo
import com.hugo.standings.domain.model.ConstructorRaceResultsInfo
import com.hugo.standings.domain.model.ConstructorStandingsInfo
import com.hugo.standings.domain.model.DriverQualifyingResultsInfo
import com.hugo.standings.domain.model.DriverRaceResultsInfo
import com.hugo.standings.domain.model.DriverStandingsInfo
import com.hugo.standings.domain.repository.IF1StandingsRepository
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class F1StandingRepositoryImpl @Inject constructor(
    private val f1StandingsApi: F1StandingsApi

): IF1StandingsRepository {
    override fun getConstructorStandings(season: String): Flow<Resource<List<ConstructorStandingsInfo>>> = flow {
        AppLogger.d(message="Inside getConstructorStandings")
        emit(Resource.Loading())
        try {
            val constructorStandings = f1StandingsApi.getConstructorStandings(season)
            emit(Resource.Success(constructorStandings.toConstructorInfoList()))
            AppLogger.d(message = "Success getting constructor standings ${constructorStandings.toConstructorInfoList().size}")

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting Constructor standings: ${e.localizedMessage}")
        }
        catch (e:HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    override fun getDriverStandings(season: String): Flow<Resource<List<DriverStandingsInfo>>> = flow {
        AppLogger.d(message = "Inside getDriverStandings")
        emit(Resource.Loading())
        try {
            val driverStandings = f1StandingsApi.getDriverStandings(season)
            emit(Resource.Success(driverStandings.toDriverStandingsInfoList()))
            AppLogger.d(message = "Success getting driver standings ${driverStandings.toDriverStandingsInfoList().size}")

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
            val driverRaceResults = f1StandingsApi.getDriverRaceResult(season = season, driverId = driverId)
            emit(Resource.Success(driverRaceResults.toDriverRaceResultInfoList()))
            AppLogger.d(message = "Success getting driver race results ${driverRaceResults.toDriverRaceResultInfoList().size}")

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

        AppLogger.d(message = "inside getDriverRaceResults")
        emit(Resource.Loading())

        try {
            val driverQualifyingResults = f1StandingsApi.getDriverQualifyingResult(season = season, driverId = driverId)
            emit(Resource.Success(driverQualifyingResults.toDriverQualifyingResultInfoList()))
            AppLogger.d(message = "Success getting driver qualifying results ${driverQualifyingResults.toDriverQualifyingResultInfoList().size}")

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting driver qualifying results: ${e.localizedMessage}")
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
            val constructorRaceResults = f1StandingsApi.getConstructorRaceResult(season = season, constructorId = constructorId)
            emit(Resource.Success(constructorRaceResults.toConstructorRaceResultInfoList()))
            AppLogger.d(message = "Success getting constructor race results ${constructorRaceResults.toConstructorRaceResultInfoList().size}")

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

        AppLogger.d(message = "inside getConstructorRaceResults")
        emit(Resource.Loading())

        try {
            val constructorQualifyingResults = f1StandingsApi.getConstructorQualifyingResult(season = season, constructorId = constructorId)
            emit(Resource.Success(constructorQualifyingResults.toConstructorQualifyingResultInfoList()))
            AppLogger.d(message = "Success getting Constructor qualifying results ${constructorQualifyingResults.toConstructorQualifyingResultInfoList().size}")

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.e(message = "Error getting Constructor qualifying results: ${e.localizedMessage}")
        }
        catch (e:HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }




}