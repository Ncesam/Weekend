package com.ncesam.sgk2026.domain.repository

import com.ncesam.sgk2026.domain.models.CartItem
import com.ncesam.sgk2026.domain.models.CartItemForm


interface ShopCartRepository {
    suspend fun getAllItem(): Result<List<CartItem>>
    suspend fun addShopCart(item: CartItemForm)
    suspend fun updateQuantity(quantity: Int, id: Int)
    suspend fun deleteAll()
    suspend fun orderItems()
}