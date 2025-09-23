package com.party.di

import com.party.data.datasource.local.datastore.DataStoreLocalSource
import com.party.data.datasource.local.room.dao.KeywordDao
import com.party.data.datasource.remote.banner.BannerRemoteSource
import com.party.data.datasource.remote.party.PartyRemoteSource
import com.party.data.datasource.remote.search.SearchRemoteSource
import com.party.data.datasource.remote.user.UserRemoteSource
import com.party.data.repository.BannerRepositoryImpl
import com.party.data.repository.DataStoreRepositoryImpl
import com.party.data.repository.KeywordRepositoryImpl
import com.party.data.repository.PartyRepositoryImpl
import com.party.data.repository.SearchRepositoryImpl
import com.party.data.repository.UserRepositoryImpl
import com.party.domain.repository.BannerRepository
import com.party.domain.repository.DataStoreRepository
import com.party.domain.repository.KeywordRepository
import com.party.domain.repository.PartyRepository
import com.party.domain.repository.SearchRepository
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
    fun provideKeywordRepository(keywordDao: KeywordDao): KeywordRepository {
        return KeywordRepositoryImpl(keywordDao)

    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStoreLocalSource: DataStoreLocalSource): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStoreLocalSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userRemoteSource: UserRemoteSource,
        dataStoreLocalSource: DataStoreLocalSource,
    ): UserRepository {
        return UserRepositoryImpl(
            userRemoteSource = userRemoteSource,
            dataStoreLocalSource = dataStoreLocalSource
        )
    }

    @Provides
    @Singleton
    fun providePartyRepository(partyRemoteSource: PartyRemoteSource): PartyRepository{
        return PartyRepositoryImpl(partyRemoteSource = partyRemoteSource)
    }

    @Provides
    @Singleton
    fun provideBannerRepository(bannerRemoteSource: BannerRemoteSource): BannerRepository{
        return BannerRepositoryImpl(bannerRemoteSource = bannerRemoteSource)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(searchRemoteSource: SearchRemoteSource): SearchRepository{
        return SearchRepositoryImpl(searchRemoteSource = searchRemoteSource)
    }
}