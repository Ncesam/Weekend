package com.ncesam.sgk2026.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import com.ncesam.sgk2026.domain.state.CreatePinCodeEffect
import com.ncesam.sgk2026.domain.state.CreatePinCodeEvent
import com.ncesam.sgk2026.domain.state.CreatePinCodeState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update


class CreatePinCodeViewModel(private val appSettingsRepository: AppSettingsRepository): ViewModel() {
    private val _state = MutableStateFlow(CreatePinCodeState())
    val state = _state.asStateFlow()

    private val _effect = Channel<CreatePinCodeEffect>()
    val effect = _effect.receiveAsFlow()


    suspend fun onEvent(event: CreatePinCodeEvent) {
        when (event) {
            is CreatePinCodeEvent.PinCodeAdd -> {
                val newPinCode = _state.value.pincode + event.value
                _state.update { state -> state.copy(pincode = newPinCode) }
            }

            is CreatePinCodeEvent.PinCodeDelete -> {
                _state.update {state -> state.copy(pincode = state.pincode.dropLast(1))}
            }

            is CreatePinCodeEvent.BiometryUsedChanged -> {
                if (event.value) {
                    _effect.send(CreatePinCodeEffect.ShowBiometric)
                }
                _state.update { state -> state.copy(biometryUsed = event.value) }
            }

            is CreatePinCodeEvent.PinCodeCreate -> {
                appSettingsRepository.setPinCode(_state.value.pincode)
                appSettingsRepository.setBiometryUsed(_state.value.biometryUsed)
                _effect.send(CreatePinCodeEffect.GoToMain)
            }
        }
    }
}