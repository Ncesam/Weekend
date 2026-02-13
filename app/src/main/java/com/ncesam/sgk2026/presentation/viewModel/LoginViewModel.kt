package com.ncesam.sgk2026.presentation.viewModel

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.ncesam.sgk2026.domain.state.LoginEffect
import com.ncesam.sgk2026.domain.state.LoginEvent
import com.ncesam.sgk2026.domain.state.LoginState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow


class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private val _effect = Channel<LoginEffect>()
    val effect = _effect.receiveAsFlow()


    suspend fun onEvent(event: LoginEvent) {
        when (event) {

        }
    }
}