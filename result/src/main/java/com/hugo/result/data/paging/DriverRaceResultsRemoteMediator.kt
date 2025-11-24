package com.hugo.result.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.hugo.datasource.local.RoomDB.datasource.DriverRace.DriverRaceLocalDataSource
import com.hugo.datasource.local.TransactionProvider
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.result.data.mapper.toDriverRaceResultInfoList
import com.hugo.result.data.remote.F1ResultApi
import com.hugo.utilities.logging.AppLogger

@OptIn(ExperimentalPagingApi::class)
class DriverRaceResultsRemoteMediator(
    private val season: String,
    private val driverId: String,
    private val apiService: F1ResultApi,
    private val localDataSource: DriverRaceLocalDataSource,
    private val transactionProvider: TransactionProvider
) : RemoteMediator<Int, DriverRaceResultsInfo>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DriverRaceResultsInfo>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> state.pages.size
        }

        AppLogger.d(message = "🔹 LoadType = $loadType | Page = $page | Offset = ${page * state.config.pageSize}")

        return try {
            val response = apiService.getDriverRaceResult(
                season = season,
                driverId = driverId,
                limit = state.config.pageSize,
                offset = page * state.config.pageSize
            )

            val results = response.toDriverRaceResultInfoList()

            transactionProvider.runAsTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteAllDriverRaceListFromDB(driverId)
                }
                localDataSource.insertDriverRaceListInDB(results)
            }

            MediatorResult.Success(endOfPaginationReached = results.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}