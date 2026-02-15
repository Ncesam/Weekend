package com.ncesam.sgk2026.domain.state

import com.ncesam.sgk2026.domain.models.PasswordRules

data class RegistrationState(
    val firstName: String = "",
    val lastName: String = "",
    val fatherName: String = "",
    val born: String = "",
    val gender: String = "",
    val email: String = "",
    val bornError: String? = null,
    val emailError: String? = null,
    val password: String = "",
    val confirmPassword: String = "",
    val passwordRules: PasswordRules = PasswordRules(),
    val passwordError: String? = null,
)


data class RegistrationInputState(
    val firstNameFocused: Boolean = false,
    val lastNameFocused: Boolean = false,
    val fatherNameFocused: Boolean = false,
    val bornFocused: Boolean = false,
    val emailFocused: Boolean = false,
    val passwordFocused: Boolean = false,
    val passwordVisibility: Boolean = false,
    val confirmPasswordFocused: Boolean = false,
    val confirmPasswordVisibility: Boolean = false
)


sealed interface RegistrationEvent {
    data class FirstNameChanged(val value: String) : RegistrationEvent
    data class LastNameChanged(val value: String) : RegistrationEvent
    data class FatherNameChanged(val value: String) : RegistrationEvent
    data class BornChanged(val value: String) : RegistrationEvent
    data class GenderChanged(val value: String) : RegistrationEvent
    data class EmailChanged(val value: String) : RegistrationEvent
    data class PasswordChanged(val value: String) : RegistrationEvent
    data class ConfirmPasswordChanged(val value: String) : RegistrationEvent
    object GoToPassword : RegistrationEvent
    object GoToLogin : RegistrationEvent
}


sealed interface RegistrationEffect {
    data class ShowSnackBar(val msg: String) : RegistrationEffect
    object GoToPassword : RegistrationEffect
    object GoToLogin : RegistrationEffect
}