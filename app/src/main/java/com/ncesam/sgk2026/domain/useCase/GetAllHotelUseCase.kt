package com.ncesam.sgk2026.domain.useCase

import com.ncesam.sgk2026.domain.models.Hotel
import com.ncesam.sgk2026.domain.repository.HotelRepository


class GetAllHotelUseCase(private val hotelRepository: HotelRepository) {
    suspend operator fun invoke(): Result<List<Hotel>> {
        return hotelRepository.getAllHotels().map { hotels -> hotels }
    }
}