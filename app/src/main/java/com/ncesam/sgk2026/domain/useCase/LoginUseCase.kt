package com.ncesam.sgk2026.domain.useCase

import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import com.ncesam.sgk2026.domain.repository.AuthRepository
import com.ncesam.sgk2026.domain.repository.TokenManager


class LoginUseCase(private val authRepository: AuthRepository, private val appSettingsRepository: AppSettingsRepository) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.login(email, password).onSuccess { user ->
            appSettingsRepository.setUserId(user.id)
        }.onFailure { exception -> throw exception }
    }
}