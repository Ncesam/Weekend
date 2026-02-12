package com.ncesam.sgk2026.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
     val userIdFlow: Flow<String>

    fun setUserId(value: String)
}