package com.ncesam.sgk2026.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.ncesam.sgk2026.domain.state.LoginEffect
import com.ncesam.sgk2026.domain.state.LoginEvent
import com.ncesam.sgk2026.domain.state.LoginState
import com.ncesam.sgk2026.domain.useCase.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update


class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private val _effect = Channel<LoginEffect>()
    val effect = _effect.receiveAsFlow()
    suspend fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _state.update { state -> state.copy(email = event.value.trim(), emailError = null) }
            }

            is LoginEvent.PasswordChanged -> {
                _state.update { state -> state.copy(password = event.value.trim(), passwordError = null) }
            }

            LoginEvent.OAuthClicked -> {
                _effect.send(LoginEffect.ShowSnackBar("Не реализовано"))
            }

            LoginEvent.RegisterClicked -> {
                _effect.send(LoginEffect.GoToRegistration)
            }

            LoginEvent.LoginClicked -> {
                try {
                    loginUseCase(email = _state.value.email.lowercase(), password = _state.value.password)
                    _effect.send(LoginEffect.GoToCreatePinCode)
                } catch (e: Exception) {
                    val emailError = "Email неверный"
                    val passwordError = "Пароль неверный"
                    _state.update { state ->
                        state.copy(
                            email = "",
                            password = "",
                            emailError = emailError,
                            passwordError = passwordError
                        )
                    }
                }

            }
        }
    }
}