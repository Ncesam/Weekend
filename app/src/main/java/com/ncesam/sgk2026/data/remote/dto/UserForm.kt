package com.ncesam.sgk2026.data.remote.dto

import kotlinx.serialization.SerialName

data class UserForm(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("father_name") val fatherName: String,
    val born: String,
    val gender: String,
    val avatar: String,
    val email: String,
    val password: String,
    val passwordConfirm: String
)