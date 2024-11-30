package com.party.di

import com.party.data.datasource.remote.banner.BannerRemoteSource
import com.party.data.datasource.remote.banner.BannerRemoteSourceImpl
import com.party.data.datasource.remote.party.PartyRemoteSource
import com.party.data.datasource.remote.party.PartyRemoteSourceImpl
import com.party.data.datasource.remote.search.SearchRemoteSource
import com.party.data.datasource.remote.search.SearchRemoteSourceImpl
import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.datasource.remote.user.UserRemoteSourceImpl
import com.party.data.repository.PartyRepositoryImpl
import com.party.data.service.NoTokenService
import com.party.data.service.PartyService
import com.party.data.service.SearchService
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
    fun provideUserRemoteSource(noTokenService: NoTokenService, userService: UserService): UserRemoteSource {
        return UserRemoteSourceImpl(noTokenService = noTokenService, userService = userService)
    }

    @Provides
    @Singleton
    fun providePartyRemoteSource(partyService: PartyService): PartyRemoteSource{
        return PartyRemoteSourceImpl(partyService = partyService)
    }

    @Provides
    @Singleton
    fun provideBannerRemoteSource(noTokenService: NoTokenService): BannerRemoteSource{
        return BannerRemoteSourceImpl(noTokenService = noTokenService)
    }

    @Provides
    @Singleton
    fun provideSearchRemoteSource(searchService: SearchService): SearchRemoteSource{
        return SearchRemoteSourceImpl(searchService = searchService)
    }
}