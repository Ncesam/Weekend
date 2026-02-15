package com.ncesam.sgk2026.domain.state

import com.ncesam.sgk2026.domain.models.Hotel

data class MainState(
    val filteredHotels: List<Hotel> = emptyList(),
    val selectedCategory: String = "",
    val categories: List<String> = emptyList(),
    val allHotels: List<Hotel> = emptyList(),
    val search: String = "",
    val selectedHotel: Hotel? = null,
    val addedHotels: List<String> = emptyList(),
    val totalShopCart: Int = 0
) {
    val shopCartButtonActive: Boolean = addedHotels.isNotEmpty()
}


sealed interface MainEvent {
    data class SearchChanged(val value: String) : MainEvent
    data class SelectHotel(val id: String) : MainEvent
    data class AddToCart(val id: String) : MainEvent
    data class SelectCategory(val value: String) : MainEvent
    object GoToSearch : MainEvent
    object GoToProfile : MainEvent
    object GoToCart : MainEvent
}


sealed interface MainEffect {
    data class GoToSearch(val value: String) : MainEffect
    data class ShowSnackBar(val msg: String) : MainEffect
    object GoToProfile : MainEffect
    object GoToCart : MainEffect
    data class GoToBooking(val hotel: Hotel) : MainEffect
}