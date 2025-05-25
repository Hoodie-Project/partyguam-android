package com.party.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.party.common.Constants.ACCESS_TOKEN_KEY
import com.party.common.Constants.ACCESS_TOKEN_PREFERENCES
import com.party.common.Constants.FCM_TOKEN_KEY
import com.party.common.Constants.FIRST_LAUNCH_FLAG_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ACCESS_TOKEN_PREFERENCES)

class DataStoreLocalSourceImpl(context: Context): DataStoreLocalSource {
    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey(ACCESS_TOKEN_KEY)
        private val FIRST_LAUNCH_FLAG = booleanPreferencesKey(FIRST_LAUNCH_FLAG_KEY)
        private val FCM_TOKEN = stringPreferencesKey(FCM_TOKEN_KEY)
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

    override suspend fun deleteAccessToken(): String {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
        }

        return ""
    }

    override suspend fun saveFirstLaunchFlag() {
        dataStore.edit { preferences ->
            preferences[FIRST_LAUNCH_FLAG] = false
        }
    }

    override fun getFirstLaunchFlag(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is Exception) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val flag = preferences[FIRST_LAUNCH_FLAG] ?: true
                flag
            }
    }

    override suspend fun saveFcmToken(token: String) {
        dataStore.edit { preferences ->
            preferences[FCM_TOKEN] = token
        }
    }

    override fun getFcmToken(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is Exception) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val flag = preferences[FCM_TOKEN] ?: ""
                flag
            }
    }
}