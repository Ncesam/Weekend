package com.ncesam.sgk2026.domain.models


data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val fatherName: String,
    val born: String,
    val gender: String,
    val avatar: String,
)