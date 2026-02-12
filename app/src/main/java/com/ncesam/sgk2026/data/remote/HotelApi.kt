package com.ncesam.sgk2026.data.remote

import com.ncesam.sgk2026.data.remote.dto.HotelDto
import com.ncesam.sgk2026.domain.models.PocketBaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface HotelApi {
    @GET("/api/collections/hotels/record")
    suspend fun getAll(
        @Header("Authorization") token: String
    ): Response<PocketBaseResponse<HotelDto>>
}