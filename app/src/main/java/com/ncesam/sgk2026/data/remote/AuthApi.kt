package com.ncesam.sgk2026.data.remote

import com.ncesam.sgk2026.data.remote.dto.LoginRequest
import com.ncesam.sgk2026.data.remote.dto.UserDto
import com.ncesam.sgk2026.data.remote.dto.UserForm
import com.ncesam.sgk2026.domain.models.PocketBaseResponse
import com.ncesam.sgk2026.domain.models.TokenWith
import com.ncesam.sgk2026.domain.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface AuthApi {
    @POST("/api/collections/users/auth-with-password")
    suspend fun login(@Body request: LoginRequest): Response<TokenWith<UserDto>>

    @POST("/api/collections/users/record")
    suspend fun register(@Body request: UserForm): Response<UserDto>

    @POST("/api/collections/users/auth-refresh")
    suspend fun refresh(@Header("Authorization") token: String): Response<TokenWith<UserDto>>
}