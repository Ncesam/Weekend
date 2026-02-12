package com.ncesam.sgk2026.data.remote

import com.ncesam.sgk2026.data.remote.dto.BookingDto
import com.ncesam.sgk2026.data.remote.dto.BookingForm
import com.ncesam.sgk2026.domain.models.PocketBaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Path


interface BookingApi {
    @GET("/api/collections/bookings/records")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("filter") filter: String,
    ): Response<PocketBaseResponse<BookingDto>>

    @POST("/api/collections/bookings/records")
    suspend fun addBooking(
        @Header("Authorization") token: String,
        @Body form: BookingForm
    ) : Response<BookingDto>

    @DELETE("/api/collections/bookings/records/{id}")
    suspend fun deleteBooking(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    )
}