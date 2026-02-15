package com.ncesam.sgk2026.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import com.ncesam.sgk2026.domain.repository.TokenManager
import com.ncesam.sgk2026.domain.state.SplashEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val tokenManager: TokenManager,
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _effect = Channel<SplashEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            if (appSettingsRepository.userIdFlow.first().isBlank()) {
                _effect.send(SplashEffect.GoToLogin)
            } else {
                tokenManager.getValidToken().onFailure {
                    _effect.send(SplashEffect.GoToLogin)
                }.onSuccess { _effect.send(SplashEffect.GoToMain) }
            }
            _isLoading.value = false

        }
    }
}