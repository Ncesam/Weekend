package com.ncesam.sgk2026.domain.state


data class PinCodeState(
    val biometryActive: Boolean = false,
    val pinCode: String = "",
)


sealed interface PinCodeEvent {
    data class PinCodeAdd(val value: String): PinCodeEvent
    object PinCodeDelete: PinCodeEvent
    object PinCodeCheck: PinCodeEvent
    object BiometryAuthClicked: PinCodeEvent
}

sealed interface PinCodeEffect {
    object BiometryClicked: PinCodeEffect
    data class ShowSnackBar(val msg: String): PinCodeEffect
    object GoToMain: PinCodeEffect
}