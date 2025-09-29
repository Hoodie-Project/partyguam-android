package com.party.data.datasource.local.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreLocalSource {

    suspend fun saveAccessToken(token: String)

    fun getAccessToken(): Flow<String>

    suspend fun deleteAccessToken(): String

    suspend fun saveFirstLaunchFlag()

    fun getFirstLaunchFlag(): Flow<Boolean>

    suspend fun saveFcmToken(token: String)

    fun getFcmToken(): Flow<String>

    suspend fun saveNickName(nickName: String)

    fun getNickName(): Flow<String>
}