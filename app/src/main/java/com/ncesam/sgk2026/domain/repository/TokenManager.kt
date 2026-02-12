package com.ncesam.sgk2026.domain.repository

interface TokenManager {
    suspend fun getValidToken(): Result<String>
    suspend fun setToken(token: String)
}