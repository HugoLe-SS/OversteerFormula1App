package com.hugo.standings.data.repository

import com.hugo.standings.data.remote.F1StandingsApi
import com.hugo.standings.data.remote.dto.toConstructorInfoList
import com.hugo.standings.data.remote.dto.toDriverQualifyingResultInfoList
import com.hugo.standings.data.remote.dto.toDriverRaceResultInfoList
import com.hugo.standings.data.remote.dto.toDriverStandingsInfoList
import com.hugo.standings.domain.model.ConstructorStandingsInfo
import com.hugo.standings.domain.model.DriverQualifyingResultInfo
import com.hugo.standings.domain.model.DriverRaceResultInfo
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

    override fun getDriverRaceResults(season: String, driverId: String): Flow<Resource<List<DriverRaceResultInfo>>> = flow {
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

    override fun getDriverQualifyingResults(season: String, driverId: String): Flow<Resource<List<DriverQualifyingResultInfo>>> = flow {
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

}