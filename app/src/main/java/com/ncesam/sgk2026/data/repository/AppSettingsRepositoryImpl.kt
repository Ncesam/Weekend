package com.ncesam.sgk2026.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class AppSettingsRepositoryImpl(private val dataStore: DataStore<Preferences>): AppSettingsRepository {

    private companion object {
        val USER_ID_KEY: Preferences.Key<String> = stringPreferencesKey("USER_ID")
        val BIOMETRY_USED_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("BIOMETRY_USED")
        val PIN_CODE_KEY: Preferences.Key<String> = stringPreferencesKey("PIN_CODE_KEY")
    }

    override val userIdFlow: Flow<String> = dataStore.data.map { preferences -> preferences[USER_ID_KEY]?: "" }

    override suspend fun setUserId(value: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = value
        }
    }

    override val biometryUsedFlow: Flow<Boolean> = dataStore.data.map { preferences -> preferences[BIOMETRY_USED_KEY]?: false }

    override suspend fun setBiometryUsed(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[BIOMETRY_USED_KEY] = value
        }
    }

    override val pinCodeFlow: Flow<String> = dataStore.data.map { preferences -> preferences[PIN_CODE_KEY] ?: "" }


    override suspend fun setPinCode(value: String) {
        dataStore.edit { preferences ->
            preferences[PIN_CODE_KEY] = value

        }
    }


}