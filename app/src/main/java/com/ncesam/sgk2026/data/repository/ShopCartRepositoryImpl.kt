package com.ncesam.sgk2026.data.repository

import com.ncesam.sgk2026.data.mappers.toDomain
import com.ncesam.sgk2026.data.remote.BookingApi
import com.ncesam.sgk2026.data.remote.dto.BookingForm
import com.ncesam.sgk2026.data.room.ShopCartDao
import com.ncesam.sgk2026.data.utils.safeApiCall
import com.ncesam.sgk2026.domain.entity.CartItemEntity
import com.ncesam.sgk2026.domain.models.CartItem
import com.ncesam.sgk2026.domain.models.CartItemForm
import com.ncesam.sgk2026.domain.repository.ShopCartRepository
import com.ncesam.sgk2026.domain.repository.TokenManager


class ShopCartRepositoryImpl(
    private val shopCartDao: ShopCartDao,
    private val bookingApi: BookingApi,
    private val tokenManager: TokenManager
) : ShopCartRepository {
    override suspend fun getAllItem(): Result<List<CartItem>> {
        val entities = shopCartDao.getAll()
        return Result.success(entities.map { entity -> entity.toDomain() })
    }

    override suspend fun addShopCart(item: CartItemForm) {
        val form = CartItemEntity(
            0,
            item.title,
            item.quantity,
            item.hotel,
            item.user,
            item.perItem,
            item.dateFrom,
            item.dateTo,
            item.phone,
            item.nameBooker
        )
        shopCartDao.addItem(form)
    }

    override suspend fun updateQuantity(
        quantity: Int,
        id: Int
    ) {
        shopCartDao.updateQuantity(id, quantity)
    }

    override suspend fun deleteAll() {
        shopCartDao.deleteAll()
    }

    override suspend fun orderItems() {
        val items = shopCartDao.getAll()
        shopCartDao.deleteAll()
        tokenManager.getValidToken().map { token ->
            items.forEach { item ->
                val form = BookingForm(
                    item.user,
                    item.dateFrom,
                    item.dateTo,
                    item.phone,
                    item.nameBooker,
                    item.hotel
                )
                for (count in 0..item.quantity) {
                    safeApiCall({ bookingApi.addBooking(token, form) }, { dto -> dto })
                }
            }
        }
    }

}