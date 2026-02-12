package com.ncesam.sgk2026.data.repository

import com.ncesam.sgk2026.data.mappers.toDomain
import com.ncesam.sgk2026.data.remote.HotelApi
import com.ncesam.sgk2026.data.utils.safeApiCall
import com.ncesam.sgk2026.domain.models.Hotel
import com.ncesam.sgk2026.domain.repository.HotelRepository
import com.ncesam.sgk2026.domain.repository.TokenManager


class HotelRepositoryImpl(private val hotelApi: HotelApi, private val tokenManager: TokenManager) :
    HotelRepository {
    override suspend fun getAllHotels(): Result<List<Hotel>> {
        return tokenManager.getValidToken().map { token ->
            return safeApiCall(
                method = { hotelApi.getAll(token) },
                mapper = { response ->
                    response.items.map { hotelDto -> hotelDto.toDomain() }
                }
            ).map { hotels -> hotels }
        }
    }

}