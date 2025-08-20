package com.hugo.news.data.repository

import com.hugo.news.data.mapper.toF1NewsArticles
import com.hugo.news.data.remote.NewsApi
import com.hugo.news.domain.model.NewsInfo
import com.hugo.news.domain.repository.INewsRepository
import com.hugo.utilities.AppError
import com.hugo.utilities.AppUtilities.toAppError
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private  val api: NewsApi
): INewsRepository {

    override fun getF1News(): Flow<Resource<List<NewsInfo>, AppError>> = flow{
        AppLogger.d(message = "Inside getF1News")

        try {
                AppLogger.d(message = "Network fetch needed for getting F1 news.")
                emit(Resource.Loading(isFetchingFromNetwork = true)) // <<< Indicate network fetch

                val f1News = api.getF1News().toF1NewsArticles()

                emit(Resource.Success(f1News))
                AppLogger.d(message = "Success getting F1 news with size ${f1News.size}")

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

}