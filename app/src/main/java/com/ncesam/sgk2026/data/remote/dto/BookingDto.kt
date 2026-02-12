package com.ncesam.sgk2026.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BookingDto(
    val id: String,
    val user: String,
    val dataFrom: String,
    val dateTo: String,
    val phone: String,
    @SerialName("name_booked") val nameBooker: String,
    val hotel: String,
)