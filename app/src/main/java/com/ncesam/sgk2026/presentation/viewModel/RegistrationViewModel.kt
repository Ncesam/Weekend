package com.ncesam.sgk2026.presentation.viewModel

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
                _state.update { state -> state.copy(firstName = event.value) }
            }

            is RegistrationEvent.LastNameChanged -> {
                _state.update { state -> state.copy(lastName = event.value) }
            }

            is RegistrationEvent.FatherNameChanged -> {
                _state.update { state -> state.copy(fatherName = event.value) }
            }

            is RegistrationEvent.BornChanged -> {
                _state.update { state -> state.copy(born = event.value, bornError = null) }
            }

            is RegistrationEvent.GenderChanged -> {
                _state.update { state -> state.copy(gender = event.value) }
            }

            is RegistrationEvent.EmailChanged -> {
                _state.update { state -> state.copy(email = event.value, emailError = null) }
            }

            is RegistrationEvent.PasswordChanged -> {
                val rules = validatePassword(event.value)
                _state.update { state ->
                    state.copy(
                        password = event.value,
                        passwordRules = rules,
                        passwordError = null
                    )
                }
            }

            is RegistrationEvent.ConfirmPasswordChanged -> {
                _state.update { state ->
                    state.copy(
                        confirmPassword = event.value,
                        passwordError = if (state.password != event.value) "Пароли не совпадают" else null
                    )
                }
            }


            RegistrationEvent.GoToLogin -> {
                try {
                    registrationUseCase(
                        UserForm(
                            _state.value.firstName,
                            _state.value.lastName,
                            _state.value.fatherName,
                            _state.value.born,
                            _state.value.gender,
                            "",
                            _state.value.email,
                            _state.value.password,
                            _state.value.confirmPassword,
                        )
                    )
                    _effect.send(RegistrationEffect.GoToLogin)
                } catch (e: Exception) {
                    _effect.send(RegistrationEffect.ShowSnackBar("Ошибка при регистрации"))
                }
            }

            RegistrationEvent.GoToPassword -> {
                val emailError = if (!validateEmail(state.value.email)) "Email неверный" else null
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
                onEvent(RegistrationEvent.GoToLogin)
                _effect.send(RegistrationEffect.GoToLogin)
            }


        }
    }

}