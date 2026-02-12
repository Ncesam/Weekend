package com.ncesam.sgk2026.data.mappers

import com.ncesam.sgk2026.data.remote.dto.HotelDto
import com.ncesam.sgk2026.domain.models.Hotel


fun HotelDto.toDomain(): Hotel {
    return Hotel(
        id,
        title,
        description,
        salesActive,
        cost,
        facilities,
        image
    )
}