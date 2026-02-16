package com.ncesam.sgk2026.domain.repository

import android.content.Context
import android.net.Uri
import com.ncesam.sgk2026.domain.models.User


interface UserRepository {
    suspend fun uploadAvatar(
        uri: Uri
    ): Result<User>

    suspend fun getUser(): Result<User>

    suspend fun getNotificationActive(): Boolean
}