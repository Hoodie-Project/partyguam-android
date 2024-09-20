package com.party.di

import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.datasource.remote.user.UserRemoteSourceImpl
import com.party.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteSourceModule {

    @Provides
    @Singleton
    fun provideUserRemoteSource(userService: UserService): UserRemoteSource {
        return UserRemoteSourceImpl(userService = userService)
    }
}