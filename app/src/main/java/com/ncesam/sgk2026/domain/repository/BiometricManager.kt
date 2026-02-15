package com.ncesam.sgk2026.domain.repository


interface BiometricManager {
    fun authenticate(
        title: String,
        description: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )
}