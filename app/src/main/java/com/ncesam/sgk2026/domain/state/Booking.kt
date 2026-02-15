package com.ncesam.sgk2026.domain.state

import com.ncesam.sgk2026.domain.models.Hotel


data class BookingState(
    val hotel: Hotel? = null,
    val userId: String = "",
    val dateFrom: String = "",
    val dateTo: String = "",
    val phone: String = "",
    val nameBooked: String = "",
    val dateFromError: String? = null,
    val dateToError: String? = null,
    val phoneError: String? = null,
)

data class BookingInputState(
    val dateFromFocused: Boolean = false,
    val dateToFocused: Boolean = false,
    val phoneFocused: Boolean = false,
    val nameBookedFocused: Boolean = false,
)


sealed interface BookingEvent {
    data class DateFromChanged(val value: String) : BookingEvent
    data class DateToChanged(val value: String) : BookingEvent
    data class PhoneChanged(val value: String) : BookingEvent
    data class NameBookedChanged(val value: String) : BookingEvent
    object AddToCart : BookingEvent
}

sealed interface BookingEffect {
    object GoToMain : BookingEffect
}