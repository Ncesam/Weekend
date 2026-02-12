package com.ncesam.sgk2026.domain.models

import kotlinx.serialization.SerialName


data class Booking(
    val id: String,
    val user: String,
    val dataFrom: String,
    val dateTo: String,
    val phone: String,
    val nameBooker: String,
    val hotel: String,
)