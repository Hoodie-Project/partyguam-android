package com.party.di

import com.party.data.datasource.local.datastore.DataStoreLocalSource
import com.party.data.datasource.remote.party.PartyRemoteSource
import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.repository.DataStoreRepositoryImpl
import com.party.data.repository.PartyRepositoryImpl
import com.party.data.repository.UserRepositoryImpl
import com.party.domain.repository.DataStoreRepository
import com.party.domain.repository.PartyRepository
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
    fun provideDataStoreRepository(dataStoreLocalSource: DataStoreLocalSource): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStoreLocalSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userRemoteSource: UserRemoteSource): UserRepository {
        return UserRepositoryImpl(userRemoteSource = userRemoteSource)
    }

    @Provides
    @Singleton
    fun providePartyRepository(partyRemoteSource: PartyRemoteSource): PartyRepository{
        return PartyRepositoryImpl(partyRemoteSource = partyRemoteSource)
    }
}