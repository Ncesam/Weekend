package com.ncesam.sgk2026.data.utils

import retrofit2.Response


suspend fun <R, T> safeApiCall(
    method: suspend () -> Response<R>,
    mapper: (R) -> T
): Result<T> {
    try{
        val response = method()
        if (response.isSuccessful) {
            val dto = response.body()
            if (dto != null) {
                val mapped = mapper(dto)
                return Result.success(mapped)
            } else {
                return Result.failure(Exception("Body is empty"))
            }
        } else {
            return Result.failure(Exception("Server return error. Status Code ${response.code()}"))
        }
    } catch (e: Exception) {
        return Result.failure(e)
    }
}