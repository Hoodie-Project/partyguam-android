package com.party.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.party.common.Constants.ACCESS_TOKEN_KEY
import com.party.common.Constants.ACCESS_TOKEN_PREFERENCES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ACCESS_TOKEN_PREFERENCES)

class DataStoreLocalSourceImpl(context: Context): DataStoreLocalSource {
    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey(ACCESS_TOKEN_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    override fun getAccessToken(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is Exception) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val token = preferences[ACCESS_TOKEN] ?: ""
                token
            }
    }
}