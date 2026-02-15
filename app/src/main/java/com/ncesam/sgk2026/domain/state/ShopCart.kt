package com.ncesam.sgk2026.domain.state

import com.ncesam.sgk2026.domain.models.CartItem


data class ShopCartState(
    val items: List<CartItem> = emptyList(),
) {
    val total = items.sumOf { item -> item.quantity * item.perItem }
}


sealed interface ShopCartEvent {
    data class Increment(val id: Int): ShopCartEvent
    data class Decrement(val id: Int): ShopCartEvent
    data class Delete(val id: Int): ShopCartEvent
    object DeleteAll: ShopCartEvent
    object Buy: ShopCartEvent
    object Back: ShopCartEvent
}

sealed interface ShopCartEffect {
    data class ShowSnackBar(val msg: String): ShopCartEffect
    object Back: ShopCartEffect
    object GoToMain: ShopCartEffect
}