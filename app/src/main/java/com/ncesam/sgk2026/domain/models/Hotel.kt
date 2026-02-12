package com.ncesam.sgk2026.domain.models

data class Hotel(
    val id: String,
    val title: String,
    val description: String,
    val salesActive: Boolean,
    val cost: Int,
    val facilities: List<String>,
    val image: String
)