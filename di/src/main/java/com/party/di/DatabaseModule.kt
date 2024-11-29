package com.party.di

import android.content.Context
import androidx.room.Room
import com.party.data.datasource.local.room.GuamDatabase
import com.party.data.datasource.local.room.dao.KeywordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GuamDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            GuamDatabase::class.java,
            GuamDatabase.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun keywordDao(database: GuamDatabase): KeywordDao {
        return database.keywordDAO()
    }
}