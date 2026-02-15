package com.ncesam.sgk2026.domain.state

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null
) {
    val isActiveButton: Boolean = email.isNotBlank() && password.isNotBlank()
}


data class LoginInputState(
    val emailFocused: Boolean = false,
    val passwordFocused: Boolean = false,
    val passwordVisible: Boolean = false
)

sealed interface LoginEvent {
    data class EmailChanged(val value: String): LoginEvent
    data class PasswordChanged(val value: String): LoginEvent
    object LoginClicked: LoginEvent
    object OAuthClicked: LoginEvent
    object RegisterClicked: LoginEvent
}

sealed interface LoginEffect {
    data class ShowSnackBar(val msg: String): LoginEffect
    object GoToCreatePinCode: LoginEffect
    object GoToRegistration: LoginEffect
}