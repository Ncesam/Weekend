package com.ncesam.sgk2026.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class HotelDto(
    val id: String,
    val title: String,
    val description: String,
    @SerialName("sales_active") val salesActive: Boolean,
    val cost: Int,
    val facilities: List<String>,
    val image: String
)