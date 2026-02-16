package com.ncesam.sgk2026.domain.repository

import androidx.datastore.preferences.core.edit
import com.ncesam.sgk2026.data.repository.AppSettingsRepositoryImpl.Companion.PIN_CODE_KEY
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    val userIdFlow: Flow<String>

    suspend fun setUserId(value: String)

    val biometryUsedFlow: Flow<Boolean>

    suspend fun setBiometryUsed(value: Boolean)

    val pinCodeFlow: Flow<String>
    suspend fun setPinCode(value: String)

    val notificationActiveFlow: Flow<Boolean>
    suspend fun setNotificationActive(value: Boolean)
}