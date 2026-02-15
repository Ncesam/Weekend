package com.ncesam.sgk2026.data.utils

import com.ncesam.sgk2026.domain.models.PasswordRules
import java.time.LocalDate
import java.time.LocalDateTime


fun validateEmail(email: String): Boolean {
    val pattern = """^\w+@\w+\.\w+$""".toRegex()
    val match = pattern.matches(email)
    return match
}

fun validatePassword(password: String): PasswordRules {
    var rules = PasswordRules()
    password.forEach { c ->
        if (c.isDigit()) {
           rules = rules.copy(hasDigits = true)
        } else if (c.isLowerCase()){
            rules = rules.copy(hasLower = true)
        } else if (c.isUpperCase()) {
            rules = rules.copy(hasUpper = true)
        } else if (c in "&?@!-") {
            rules = rules.copy(hasSymbol = true)
        }
    }
    if (password.length > 8){
        rules = rules.copy(hasMinLength = true)
    }
    return rules
}

fun validateDate(date: String): LocalDate? {
    return try {
        LocalDate.parse(date, POCKETBASE_FORMATTER)
    } catch (e: Exception) {
        null
    }
}

fun validatePhone(phone: String): Boolean {
    val pattern = """^(?:\+7|8)\d{10}$""".toRegex()
    return pattern.matches(phone)
}