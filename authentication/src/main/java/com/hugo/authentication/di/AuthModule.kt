package com.hugo.authentication.di

import com.hugo.authentication.data.repository.GoogleAuthRepositoryImpl
import com.hugo.authentication.data.repository.UserProfileRepositoryImpl
import com.hugo.authentication.domain.repository.GoogleAuthRepository
import com.hugo.authentication.domain.repository.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
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