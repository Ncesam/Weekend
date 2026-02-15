package com.ncesam.sgk2026.domain.models

data class PasswordRules(
    val hasDigits: Boolean = false,
    val hasUpper: Boolean = false,
    val hasLower : Boolean = false,
val hasSymbol : Boolean = false,
val hasMinLength : Boolean = false,
) {
    val passed = hasMinLength && hasUpper && hasLower && hasSymbol && hasDigits
}