package com.ncesam.sgk2026.data.repository

import com.ncesam.sgk2026.data.mappers.toDomain
import com.ncesam.sgk2026.data.remote.AuthApi
import com.ncesam.sgk2026.data.remote.dto.LoginRequest
import com.ncesam.sgk2026.data.remote.dto.UserForm
import com.ncesam.sgk2026.data.utils.safeApiCall
import com.ncesam.sgk2026.domain.models.TokenWith
import com.ncesam.sgk2026.domain.models.User
import com.ncesam.sgk2026.domain.repository.AuthRepository
import com.ncesam.sgk2026.domain.repository.TokenManager

class AuthRepositoryImpl(private val authApi: AuthApi, private val tokenManager: TokenManager) :
    AuthRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return safeApiCall(
            { authApi.login(LoginRequest(email, password)) },
            { tokenWith ->
                val mappedRecord = tokenWith.record.toDomain()
                TokenWith(tokenWith.token, mappedRecord)
            }
        ).map { tokenWith ->
            tokenManager.setToken(tokenWith.token)
            tokenWith.record
        }
    }

    override suspend fun register(form: UserForm): Result<User> {
        return safeApiCall(
            { authApi.register(form) },
            { userDto -> userDto.toDomain() }
        ).map { user -> user }
    }

}