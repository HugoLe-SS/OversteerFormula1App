package com.hugo.oversteerf1.di

import com.hugo.oversteerf1.BuildConfig
import com.hugo.oversteerf1.data.remote.F1NewsApi
import com.hugo.oversteerf1.data.repository.F1NewsRepositoryImpl
import com.hugo.oversteerf1.domain.repository.IF1NewsRepository
import com.hugo.utilities.constants.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideF1NewsApi(): F1NewsApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val headerInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-rapidapi-key", BuildConfig.RAPID_API_KEY)
                .addHeader("x-rapidapi-host", "f1-latest-news.p.rapidapi.com")
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL_F1_NEWS)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(F1NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository (api: F1NewsApi) : IF1NewsRepository {
        return F1NewsRepositoryImpl(api)
    }
}
