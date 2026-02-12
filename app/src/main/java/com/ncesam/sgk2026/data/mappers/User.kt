package com.ncesam.sgk2026.data.mappers

import com.ncesam.sgk2026.data.remote.dto.UserDto
import com.ncesam.sgk2026.domain.models.User

fun UserDto.toDomain(): User {
    return User(
        id,
        email,
        firstName,
        lastName,
        fatherName,
        born,
        gender,
        avatar
    )
}