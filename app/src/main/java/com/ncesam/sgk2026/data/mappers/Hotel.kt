package com.ncesam.sgk2026.data.mappers

import com.ncesam.sgk2026.data.remote.dto.HotelDto
import com.ncesam.sgk2026.di.BASE_URL
import com.ncesam.sgk2026.domain.models.Hotel


fun HotelDto.toDomain(): Hotel {
    return Hotel(
        id,
        title,
        description,
        salesActive,
        cost,
        facilities,
        image = "${BASE_URL}api/files/$collectionId/$id/$image",
        category
    )
}