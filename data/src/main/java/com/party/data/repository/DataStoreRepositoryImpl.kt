package com.party.data.repository

import com.party.data.datasource.local.datastore.DataStoreLocalSource
import com.party.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStoreLocalSource: DataStoreLocalSource,
): DataStoreRepository {
    override suspend fun saveAccessToken(token: String) {
        dataStoreLocalSource.saveAccessToken(token = token)
    }

    override fun getAccessToken(): Flow<String> {
        return dataStoreLocalSource.getAccessToken()
    }
}