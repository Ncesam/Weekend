package com.ncesam.sgk2026.data.mappers

import com.ncesam.sgk2026.data.remote.dto.BookingDto
import com.ncesam.sgk2026.domain.models.Booking


fun BookingDto.toDomain(): Booking {
    return Booking(
        id,
        user,
        dataFrom,
        dateTo,
        phone,
        nameBooker,
        hotel
    )
}