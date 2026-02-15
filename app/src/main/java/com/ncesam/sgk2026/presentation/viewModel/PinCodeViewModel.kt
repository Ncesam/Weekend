package com.ncesam.sgk2026.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import com.ncesam.sgk2026.domain.state.PinCodeEffect
import com.ncesam.sgk2026.domain.state.PinCodeEvent
import com.ncesam.sgk2026.domain.state.PinCodeState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PinCodeViewModel(private val appSettingsRepository: AppSettingsRepository) : ViewModel() {
    private val _state = MutableStateFlow(PinCodeState())
    val state = _state.asStateFlow()

    private val _effect = Channel<PinCodeEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            _state.update { state -> state.copy(biometryActive = appSettingsRepository.biometryUsedFlow.first()) }
        }
    }

    suspend fun onEvent(event: PinCodeEvent) {
        when (event) {
            PinCodeEvent.BiometryAuthClicked -> {
                _effect.send(PinCodeEffect.BiometryClicked)
            }

            is PinCodeEvent.PinCodeAdd -> {
                val newPinCode = _state.value.pinCode + event.value
                _state.update { state -> state.copy(pinCode = newPinCode) }
                if (newPinCode.length >= 4) {
                    onEvent(PinCodeEvent.PinCodeCheck)
                }

            }
            PinCodeEvent.PinCodeDelete -> {
                _state.update { state -> state.copy(pinCode = state.pinCode.dropLast(1)) }
            }

            PinCodeEvent.PinCodeCheck -> {
                if (appSettingsRepository.pinCodeFlow.first() == _state.value.pinCode) {
                    _effect.send(PinCodeEffect.GoToMain)
                } else {
                    _effect.send(PinCodeEffect.ShowSnackBar("Pin Code неправильный"))
                    _state.update { state -> state.copy(pinCode = "") }
                }
            }
        }
    }
}