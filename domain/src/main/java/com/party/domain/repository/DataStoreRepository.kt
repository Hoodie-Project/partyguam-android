package com.party.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveAccessToken(token: String)

    fun getAccessToken(): Flow<String>
}