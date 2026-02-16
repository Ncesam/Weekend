package com.ncesam.sgk2026.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val collectionId: String,
    val id: String,
    val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("father_name") val fatherName: String,
    val born: String,
    val gender: String,
    val avatar: String,
)