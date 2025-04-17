package com.hugo.oversteerf1.repository

import com.hugo.datasource.local.entity.F1News
import com.hugo.oversteerf1.domain.repository.IF1NewsRepository
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class F1NewsRepositoryImpl @Inject constructor(
    private val f1NewsApi: F1NewsApi
): IF1NewsRepository {

    override fun getF1News(): Flow<Resource<List<F1News>>> = flow {

        AppLogger.d(message=" inside F1NewsRepositoryImpl")
        emit(Resource.Loading())
        try{
            val f1News = f1NewsApi.getF1News()
            emit(Resource.Success(f1News))
            AppLogger.d(message = "Success getting F1 news")
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            AppLogger.d(message = "Error getting F1 news: ${e.localizedMessage}")
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach the servers, check your Internet connection"))
        }
    }
}