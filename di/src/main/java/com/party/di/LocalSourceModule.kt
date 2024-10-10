package com.party.di

import android.content.Context
import com.party.data.datasource.local.datastore.DataStoreLocalSource
import com.party.data.datasource.local.datastore.DataStoreLocalSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalSourceModule {

    @Provides
    @Singleton
    fun provideDataStoreLocalSource(@ApplicationContext context: Context): DataStoreLocalSource {
        return DataStoreLocalSourceImpl(context)
    }
}