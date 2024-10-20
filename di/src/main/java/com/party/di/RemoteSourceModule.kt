package com.party.di

import com.party.data.datasource.remote.party.PartyRemoteSource
import com.party.data.datasource.remote.party.PartyRemoteSourceImpl
import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.datasource.remote.user.UserRemoteSourceImpl
import com.party.data.repository.PartyRepositoryImpl
import com.party.data.service.PartyService
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

    @Provides
    @Singleton
    fun providePartyRemoteSource(partyService: PartyService): PartyRemoteSource{
        return PartyRemoteSourceImpl(partyService = partyService)
    }
}