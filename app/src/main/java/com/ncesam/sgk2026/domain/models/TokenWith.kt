package com.ncesam.sgk2026.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class TokenWith<T: Any>(
    val token: String,
    val record: T
)