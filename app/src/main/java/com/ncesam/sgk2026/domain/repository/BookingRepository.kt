package com.ncesam.sgk2026.domain.repository

import com.ncesam.sgk2026.domain.models.Booking


interface BookingRepository {
    suspend fun getAll(): Result<List<Booking>>
    suspend fun deleteBooking(id: String)
}


