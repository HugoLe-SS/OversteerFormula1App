package com.hugo.authentication.di

import android.content.Context
import com.hugo.authentication.data.local.UserPreferences
import com.hugo.authentication.data.repository.GoogleAuthRepositoryImpl
import com.hugo.authentication.data.repository.UserProfileRepositoryImpl
import com.hugo.authentication.domain.repository.GoogleAuthRepository
import com.hugo.authentication.domain.repository.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthBindingModule {

    @Binds
    @Singleton
    abstract fun bindGoogleAuthRepository(
        googleAuthRepositoryImpl: GoogleAuthRepositoryImpl
    ): GoogleAuthRepository

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(
        userProfileRepositoryImpl: UserProfileRepositoryImpl
    ): UserProfileRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AuthProviderModule {

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): UserPreferences {
        return UserPreferences(context)
    }
}