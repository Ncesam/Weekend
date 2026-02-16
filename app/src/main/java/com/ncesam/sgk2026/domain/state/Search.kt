package com.ncesam.sgk2026.domain.state

import com.ncesam.sgk2026.domain.models.Hotel

data class SearchState(
    val allHotels: List<Hotel> = emptyList(),
    val filteredHotels: List<Hotel> = emptyList(),
    val searchValue: String = "",
    val selectedHotel: Hotel? = null,
)


sealed interface SearchEvent {
    object Search: SearchEvent
    data class SearchValueChanged(val value: String): SearchEvent
    data class SelectHotel(val id: String): SearchEvent
    data class GoToBooking(val hotel: Hotel): SearchEvent
    data class AddToCart(val id: String): SearchEvent
}

sealed interface SearchEffect {
    data class GoToBooking(val hotel: Hotel): SearchEffect
    data class ShowSnackBar(val msg: String): SearchEffect
}