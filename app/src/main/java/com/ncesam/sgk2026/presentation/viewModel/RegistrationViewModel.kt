package com.ncesam.sgk2026.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ncesam.sgk2026.data.remote.dto.UserForm
import com.ncesam.sgk2026.data.utils.validateDate
import com.ncesam.sgk2026.data.utils.validateEmail
import com.ncesam.sgk2026.data.utils.validatePassword
import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import com.ncesam.sgk2026.domain.state.RegistrationEffect
import com.ncesam.sgk2026.domain.state.RegistrationEvent
import com.ncesam.sgk2026.domain.state.RegistrationState
import com.ncesam.sgk2026.domain.useCase.RegistrationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update


class RegistrationViewModel(private val registrationUseCase: RegistrationUseCase) : ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _effect = Channel<RegistrationEffect>()
    val effect = _effect.receiveAsFlow()

    suspend fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.FirstNameChanged -> {
                _state.update { state -> state.copy(firstName = event.value.trim()) }
            }

            is RegistrationEvent.LastNameChanged -> {
                _state.update { state -> state.copy(lastName = event.value.trim()) }
            }

            is RegistrationEvent.FatherNameChanged -> {
                _state.update { state -> state.copy(fatherName = event.value.trim()) }
            }

            is RegistrationEvent.BornChanged -> {
                _state.update { state -> state.copy(born = event.value.trim(), bornError = null) }
            }

            is RegistrationEvent.GenderChanged -> {
                _state.update { state -> state.copy(gender = event.value.trim()) }
            }

            is RegistrationEvent.EmailChanged -> {
                _state.update { state -> state.copy(email = event.value.trim(), emailError = null) }
            }

            is RegistrationEvent.PasswordChanged -> {
                val rules = validatePassword(event.value.trim())
                _state.update { state ->
                    state.copy(
                        password = event.value.trim(),
                        passwordRules = rules,
                        passwordError = null
                    )
                }
            }

            is RegistrationEvent.ConfirmPasswordChanged -> {
                _state.update { state ->
                    state.copy(
                        confirmPassword = event.value.trim(),
                        passwordError = if (state.password != event.value.trim()) "Пароли не совпадают" else null
                    )
                }
            }


            RegistrationEvent.GoToLogin -> {
                try {
                    if (!_state.value.passwordRules.passed) {
                        return
                    }
                    registrationUseCase(
                        UserForm(
                            _state.value.firstName,
                            _state.value.lastName,
                            _state.value.fatherName,
                            _state.value.born,
                            true,
                            _state.value.gender,
                            "",
                            _state.value.email.lowercase(),
                            _state.value.password,
                            _state.value.confirmPassword,
                        )
                    )
                    _effect.send(RegistrationEffect.GoToLogin)
                } catch (e: Exception) {
                    Log.e("Test", e.message ?: "")
                    _effect.send(RegistrationEffect.ShowSnackBar("Ошибка при регистрации"))
                }
            }

            RegistrationEvent.GoToPassword -> {
                val emailError = if (!validateEmail(state.value.email.lowercase())) "Email неверный" else null
                val bornError =
                    if (validateDate(state.value.born) == null) "Дата неверная (2026-02-14)" else null
                if (listOf(emailError, bornError).any { value -> value != null }) {
                    _state.update { state ->
                        state.copy(
                            born = "",
                            email = "",
                            emailError = emailError,
                            bornError = bornError
                        )
                    }
                    return
                }
                _effect.send(RegistrationEffect.GoToPassword)
            }


        }
    }

}