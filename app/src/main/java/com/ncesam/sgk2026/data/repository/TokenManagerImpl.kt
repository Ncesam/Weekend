package com.ncesam.sgk2026.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ncesam.sgk2026.data.remote.AuthApi
import com.ncesam.sgk2026.data.utils.safeApiCall
import com.ncesam.sgk2026.domain.repository.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.util.Base64


val json = Json { ignoreUnknownKeys = true }

class TokenManagerImpl(
    private val dataStore: DataStore<Preferences>,
    private val authApi: AuthApi
) : TokenManager {

    private companion object {
        val TOKEN_KEY: Preferences.Key<String> = stringPreferencesKey("TOKEN_AUTH")
    }

    override suspend fun getValidToken(): Result<String> {
        val token = getToken() ?: return Result.failure(Exception("Token is empty"))
        return if (checkExpiredToken(token)) {
            safeApiCall(
                { authApi.refresh(token) },
                { tokenWith ->
                    tokenWith.token
                }
            ).map { token -> token }
        } else {
            Result.success(token)
        }
    }


    private suspend fun getToken(): String? {
        return dataStore.data.first()[TOKEN_KEY]
    }

    private fun checkExpiredToken(token: String): Boolean {
        return try {
            val payloadRaw = token.split(".")[1]
            val payloadJson = String(Base64.getUrlDecoder().decode(payloadRaw))
            val payload = JSONObject(payloadJson)

            val exp = payload.getLong("exp")

            val numberSeconds = System.currentTimeMillis() / 1000

            numberSeconds > (exp - 60L)
        } catch (e: Exception) {
            true
        }
    }

    override suspend fun setToken(token: String) {
        dataStore.edit { preferences -> preferences[TOKEN_KEY] = token }
    }


}