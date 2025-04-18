package com.hugo.standings.data.repository

import com.hugo.standings.data.remote.F1StandingsApi
import com.hugo.standings.data.remote.dto.toConstructorInfoList
import com.hugo.standings.data.remote.dto.toDriverStandingsInfoList
import com.hugo.standings.domain.model.ConstructorStandingsInfo
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
        AppLogger.d(message=" inside getConstructorStandings")
        emit(Resource.Loading())
        try {
            val constructorStandings = f1StandingsApi.getConstructorStandings(season)
            emit(Resource.Success(constructorStandings.toConstructorInfoList()))
            AppLogger.d(message = "Success getting constructor standings ${constructorStandings.toConstructorInfoList().size}")

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.d(message = "Error getting Constructor standings: ${e.localizedMessage}")
        }
        catch (e:HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

    override fun getDriverStandings(season: String): Flow<Resource<List<DriverStandingsInfo>>> = flow {
        AppLogger.d(message = "inside getDriverStandings")
        emit(Resource.Loading())
        try {
            val driverStandings = f1StandingsApi.getDriverStandings(season)
            emit(Resource.Success(driverStandings.toDriverStandingsInfoList()))
            AppLogger.d(message = "Success getting driver standings ${driverStandings.toDriverStandingsInfoList().size}")

        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.d(message = "Error getting Constructor standings: ${e.localizedMessage}")
        }
        catch (e:HttpException)
        {
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }

}