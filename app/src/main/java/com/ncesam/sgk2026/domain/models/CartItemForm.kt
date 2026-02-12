package com.ncesam.sgk2026.domain.models

data class CartItemForm(
    val title: String,
    val quantity: Int,
    val hotel: String,
    val user: String,
    val perItem: Int,
    val dateFrom: String,
    val dateTo: String,
    val phone: String,
    val nameBooker: String,
)