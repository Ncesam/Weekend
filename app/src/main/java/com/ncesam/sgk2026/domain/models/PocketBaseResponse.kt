package com.ncesam.sgk2026.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PocketBaseResponse<T: Any>(
    val items: List<T>,
    val totalPages: Int,
    val totalItems: Int,
    val page: Int,
    val perPage: Int
)