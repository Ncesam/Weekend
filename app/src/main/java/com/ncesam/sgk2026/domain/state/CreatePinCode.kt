package com.ncesam.sgk2026.domain.state


data class CreatePinCodeState(
    val pincode: String = "",
    val biometryUsed: Boolean = false,
) {
    val biometryShow: Boolean = pincode.length >= 4
}


sealed interface CreatePinCodeEvent {
    data class PinCodeAdd(val value: String): CreatePinCodeEvent
    object PinCodeDelete: CreatePinCodeEvent
    data class BiometryUsedChanged(val value: Boolean): CreatePinCodeEvent
    object PinCodeCreate: CreatePinCodeEvent
}


sealed interface CreatePinCodeEffect {
    object GoToMain: CreatePinCodeEffect
    object ShowBiometric: CreatePinCodeEffect
}