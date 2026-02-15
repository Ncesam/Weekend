package com.ncesam.sgk2026.data.remote

import com.ncesam.sgk2026.data.remote.dto.HotelDto
import com.ncesam.sgk2026.domain.models.PocketBaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface HotelApi {
    @GET("/api/collections/hotels/records")
    suspend fun getAll(
        @Header("Authorization") token: String
    ): Response<PocketBaseResponse<HotelDto>>

    @GET("/api/collections/hotels/records/{id}")
    suspend fun getHotel(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Response<HotelDto>
}