package com.party.di

import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.repository.UserRepositoryImpl
import com.party.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userRemoteSource: UserRemoteSource): UserRepository {
        return UserRepositoryImpl(userRemoteSource = userRemoteSource)
    }
}