package com.hugo.result.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.hugo.datasource.local.RoomDB.LocalDataSource
import com.hugo.datasource.local.TransactionProvider
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.result.data.mapper.toConstructorRaceResultInfoList
import com.hugo.result.data.remote.F1ResultApi
import com.hugo.utilities.logging.AppLogger
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ConstructorRaceResultsRemoteMediator @Inject constructor(
    private val season: String,
    private val constructorId: String,
    private val apiService: F1ResultApi,
    private val localDataSource: LocalDataSource,
    private val transactionProvider: TransactionProvider
): RemoteMediator<Int, ConstructorRaceResultsInfo>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ConstructorRaceResultsInfo>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> state.pages.size
        }

        AppLogger.d(message = "🔹 LoadType = $loadType | Page = $page | Offset = ${page * state.config.pageSize}")

        return try {
            val response = apiService.getConstructorRaceResult(
                season = season,
                constructorId = constructorId,
                limit = state.config.pageSize,
                offset = page * state.config.pageSize
            )

            val results = response.toConstructorRaceResultInfoList()

            transactionProvider.runAsTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteAllConstructorRaceListFromDB(constructorId)
                }
                localDataSource.insertConstructorRaceListInDB(results)
            }

            MediatorResult.Success(endOfPaginationReached = results.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}