package com.ncesam.sgk2026.domain.repository

import com.ncesam.sgk2026.domain.models.CartItem
import com.ncesam.sgk2026.domain.models.CartItemForm
import kotlinx.coroutines.flow.Flow


interface ShopCartRepository {
    suspend fun getAllItem(): Result<Flow<List<CartItem>>>
    suspend fun addShopCart(item: CartItemForm)
    suspend fun updateQuantity(quantity: Int, id: Int)
    suspend fun deleteAll()
    suspend fun delete(id: Int)
    suspend fun orderItems()
}