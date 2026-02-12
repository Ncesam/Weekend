package com.ncesam.sgk2026.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopCart")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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