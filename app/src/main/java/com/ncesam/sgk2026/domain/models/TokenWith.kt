package com.ncesam.sgk2026.domain.models

data class TokenWith<T: Any>(
    val token: String,
    val record: T
)