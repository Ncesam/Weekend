package com.ncesam.sgk2026.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ncesam.sgk2026.domain.entity.CartItemEntity


@Dao
interface ShopCartDao {
    @Insert
    suspend fun addItem(item: CartItemEntity)

    @Query("UPDATE shopCart SET quantity=:quantity WHERE id=:id ")
    suspend fun updateQuantity(id: Int, quantity: Int)

    @Query("SELECT * FROM shopCart")
    suspend fun getAll(): List<CartItemEntity>

    @Query("DELETE FROM shopCart WHERE id=:id")
    suspend fun deleteItem(id: Int)

    @Query("DELETE FROM shopCart")
    suspend fun deleteAll()
}