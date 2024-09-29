package com.ahrorovk.duaforpeople.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences_name")
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token_key")
        private val FCM_TOKEN_KEY = stringPreferencesKey("fcm_token_key")
    }


    suspend fun updateFcmTokenKey(token: String) {
        context.dataStore.edit { preference ->
            preference[FCM_TOKEN_KEY] = token
        }
    }

    suspend fun updateAccessToken(token: String) {
        context.dataStore.edit { preference ->
            preference[ACCESS_TOKEN_KEY] = token
        }
    }

    val getFcmTokenKey = context.dataStore.data.map {
        it[FCM_TOKEN_KEY] ?: ""
    }

    val getAccessToken = context.dataStore.data.map {
        it[ACCESS_TOKEN_KEY] ?: ""
    }

}