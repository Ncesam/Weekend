package com.ncesam.sgk2026.data.remote

import com.ncesam.sgk2026.data.remote.dto.UserDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path


interface UserApi {
    @Multipart
    @PATCH("/api/collections/users/records/{id}")
    suspend fun uploadAvatar(
        @Path("id") id: String,
        @Part avatar: MultipartBody.Part
    ): Response<UserDto>


    @GET("/api/collections/users/records/{id}")
    suspend fun getUser(
        @Path("id") id: String
    ): Response<UserDto>

}