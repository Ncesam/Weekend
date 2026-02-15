package com.ncesam.sgk2026.domain.repository

import com.ncesam.sgk2026.domain.models.Hotel


interface HotelRepository {
    suspend fun getAllHotels(): Result<List<Hotel>>
    suspend fun getHotel(id: String): Result<Hotel>
}