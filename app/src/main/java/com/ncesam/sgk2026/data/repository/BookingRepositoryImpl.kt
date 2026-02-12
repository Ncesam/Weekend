package com.ncesam.sgk2026.data.repository

import com.ncesam.sgk2026.data.mappers.toDomain
import com.ncesam.sgk2026.data.remote.BookingApi
import com.ncesam.sgk2026.data.utils.safeApiCall
import com.ncesam.sgk2026.domain.models.Booking
import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import com.ncesam.sgk2026.domain.repository.BookingRepository
import com.ncesam.sgk2026.domain.repository.TokenManager
import kotlinx.coroutines.flow.first


class BookingRepositoryImpl(
    private val bookingApi: BookingApi,
    private val tokenManager: TokenManager,
    private val appSettingsRepository: AppSettingsRepository,
) : BookingRepository {
    override suspend fun deleteBooking(id: String) {
        tokenManager.getValidToken().onSuccess { token ->
            bookingApi.deleteBooking(token, id)
        }
    }

    override suspend fun getAll(): Result<List<Booking>> {
        return tokenManager.getValidToken().map { token ->
            val user = appSettingsRepository.userIdFlow.first()
            return safeApiCall(method = {
                bookingApi.getAll(token, "user=$user")
            }, mapper = { response ->
                response.items.map { bookingDto -> bookingDto.toDomain() }
            }).map { bookings -> bookings }
        }
    }
}