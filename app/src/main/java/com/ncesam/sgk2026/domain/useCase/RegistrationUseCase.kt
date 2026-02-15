package com.ncesam.sgk2026.domain.useCase

import com.ncesam.sgk2026.data.remote.dto.UserForm
import com.ncesam.sgk2026.domain.repository.AuthRepository


class RegistrationUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(form: UserForm) {
        authRepository.register(form).onFailure { exception -> throw exception }
    }
}