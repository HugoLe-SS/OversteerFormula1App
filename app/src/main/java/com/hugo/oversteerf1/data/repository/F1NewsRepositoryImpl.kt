package com.hugo.oversteerf1.data.repository

import com.hugo.datasource.local.RoomDB.LocalDataSource
import com.hugo.datasource.local.entity.F1HomeDetails
import com.hugo.datasource.local.entity.F1News
import com.hugo.oversteerf1.data.remote.F1NewsApi
import com.hugo.oversteerf1.domain.repository.IF1NewsRepository
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
import okio.IOException
import javax.inject.Inject

class F1NewsRepositoryImpl @Inject constructor(
    private val f1NewsApi: F1NewsApi,
    private val supabaseClient: SupabaseClient,
    private val localDataSource: LocalDataSource
): IF1NewsRepository {

    override fun getF1News(): Flow<Resource<List<F1News>, AppError>> = flow {

        AppLogger.d(message=" inside F1NewsRepositoryImpl")
        emit(Resource.Loading())
        try{
            val f1News = f1NewsApi.getF1News()
            emit(Resource.Success(f1News))
            AppLogger.d(message = "Success getting F1 news")
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

    override fun getF1HomeDetails(): Flow<Resource<List<F1HomeDetails>?, AppError>> = flow {
        AppLogger.d(message = "Inside getF1HomeDetails")
        try {

            if (!AppLaunchManager.hasFetchedHomeDetails) {
                AppLogger.d(message = "Network fetch needed for getting F1 circuit details.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val result = supabaseClient.postgrest["HomeDetails"].select()

                val f1HomeDetails = result.decodeList<F1HomeDetails>()

                if (f1HomeDetails.isNotEmpty()) {
                    AppLogger.d(message = "F1 Home Details saved to DB")
                    insertF1HomeDetails(f1HomeDetails)
                    AppLaunchManager.hasFetchedHomeDetails = true
                }

                emit(Resource.Success(f1HomeDetails))

            } else {
                emit(Resource.Loading(isFetchingFromNetwork = false)) // no network fetch, so no loading indicator

                val circuitDetailsFromDB = getF1HomeDetailsFromDB()
                emit(Resource.Success(circuitDetailsFromDB))
                AppLogger.d(message = "Success getting Home details from DB")
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
    }

    private suspend fun insertF1HomeDetails(f1HomeDetails: List<F1HomeDetails>){
        withContext(Dispatchers.IO){
            localDataSource.insertF1HomeDetailsInDB(f1HomeDetails)
            AppLogger.d(message = "Success insertF1HomeDetails with size:  ${f1HomeDetails.size}")
        }
    }

    private suspend fun getF1HomeDetailsFromDB(): List<F1HomeDetails>? {
        return withContext(Dispatchers.IO){
            localDataSource.getF1HomeDetailsFromDB()
        }
    }

}