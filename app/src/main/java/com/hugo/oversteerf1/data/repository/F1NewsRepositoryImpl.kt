package com.hugo.oversteerf1.data.repository

import com.hugo.datasource.local.entity.F1News
import com.hugo.oversteerf1.data.remote.F1NewsApi
import com.hugo.oversteerf1.domain.repository.IF1NewsRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.AppUtilities.toAppError
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class F1NewsRepositoryImpl @Inject constructor(
    private val f1NewsApi: F1NewsApi
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
    }
}