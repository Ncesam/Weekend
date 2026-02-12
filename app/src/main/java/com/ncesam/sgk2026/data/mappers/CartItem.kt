package com.ncesam.sgk2026.data.mappers

import com.ncesam.sgk2026.domain.entity.CartItemEntity
import com.ncesam.sgk2026.domain.models.CartItem


fun CartItemEntity.toDomain(): CartItem {
    return CartItem(
        id,
        title,
        quantity,
        hotel,
        user,
        perItem,
        dateFrom,
        dateTo,
        phone,
        nameBooker
    )
}