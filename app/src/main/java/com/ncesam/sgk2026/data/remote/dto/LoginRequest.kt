package com.ncesam.sgk2026.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    val identity: String,
    val password: String,
)