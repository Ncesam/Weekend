package com.ncesam.sgk2026.domain.repository

import com.ncesam.sgk2026.data.remote.dto.UserForm
import com.ncesam.sgk2026.domain.models.User


interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(form: UserForm): Result<User>
}
