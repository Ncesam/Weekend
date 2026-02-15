package com.ncesam.sgk2026.domain.state

import com.ncesam.sgk2026.domain.models.Hotel

data class TravelsState(
    val items: List<Hotel> = emptyList(),
)

sealed interface TravelsEvent {
    object AddClicked: TravelsEvent
    data class DeleteTravel(val id: String): TravelsEvent
}

sealed interface TravelsEffect {
    data class ShowSnackBar(val msg: String): TravelsEffect
}

