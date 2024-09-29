package com.ahrorovk.duaforpeople.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences_name")
        private val FIRST_OPEN_KEY = booleanPreferencesKey("first_open_key")
        val THE_FIRST_LOCALE_LANGUAGE_KEY = intPreferencesKey("THE_FIRST_LOCALE_LANGUAGE_KEY")
        val LANGUAGE_SHORTCUT_KEY = stringPreferencesKey("LANGUAGE_SHORTCUT_KEY")
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token_key")
        private val IS_ADDED_KEY = booleanPreferencesKey("is_added_key")
        private val ROLE_ID = intPreferencesKey("role_id")
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

    suspend fun updateIsAdded(isAdded: Boolean) {
        context.dataStore.edit { preference ->
            preference[IS_ADDED_KEY] = isAdded
        }
    }

    suspend fun updateRoleId(id: Int) {
        context.dataStore.edit { preference ->
            preference[ROLE_ID] = id
        }
    }

    suspend fun updateLanguage(id: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_SHORTCUT_KEY] = id
        }
    }

    suspend fun updateLanguageState(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[THE_FIRST_LOCALE_LANGUAGE_KEY] = id
        }
    }

    suspend fun updateFirstOpenKey(key: Boolean) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[FIRST_OPEN_KEY] = key
        }
    }


    val getFcmTokenKey = context.dataStore.data.map {
        it[FCM_TOKEN_KEY] ?: ""
    }
    val getAccessToken = context.dataStore.data.map {
        it[ACCESS_TOKEN_KEY] ?: ""
    }
    val getIsAdded = context.dataStore.data.map {
        it[IS_ADDED_KEY] ?: false
    }
    val getRoleId = context.dataStore.data.map {
        it[ROLE_ID] ?: 0
    }

    val getLanguageState = context.dataStore.data.map {
        it[THE_FIRST_LOCALE_LANGUAGE_KEY] ?: 0
    }

    val getLanguageId = context.dataStore.data.map {
        it[LANGUAGE_SHORTCUT_KEY] ?: "tg"
    }

    val getFirstOpenKey = context.dataStore.data.map {
        it[FIRST_OPEN_KEY] ?: false
    }

}