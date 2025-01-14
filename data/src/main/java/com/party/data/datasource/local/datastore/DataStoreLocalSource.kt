package com.party.data.datasource.local.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreLocalSource {

    suspend fun saveAccessToken(token: String)

    fun getAccessToken(): Flow<String>

    suspend fun deleteAccessToken(): String
}