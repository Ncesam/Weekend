package com.ncesam.sgk2026.data.repository

import android.content.Context
import android.net.Uri
import com.ncesam.sgk2026.data.mappers.toDomain
import com.ncesam.sgk2026.data.remote.UserApi
import com.ncesam.sgk2026.data.utils.safeApiCall
import com.ncesam.sgk2026.domain.models.User
import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import com.ncesam.sgk2026.domain.repository.TokenManager
import com.ncesam.sgk2026.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody


class UserRepositoryImpl(
    private val context: Context,
    private val userApi: UserApi,
    private val appSettingsRepository: AppSettingsRepository,
    private val tokenManager: TokenManager
) : UserRepository {
    override suspend fun uploadAvatar(uri: Uri): Result<User> {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return Result.failure(Exception("Not found URI"))

        val image = inputStream.readBytes().toRequestBody("image/*".toMediaType())

        val filename = "avatar_${System.currentTimeMillis()}"
        
        val part = MultipartBody.Part.createFormData("avatar", filename, image)

        val userId = appSettingsRepository.userIdFlow.first()
        return safeApiCall(
            method = {userApi.uploadAvatar(userId, part)},
            mapper = {dto -> dto.toDomain()}
        ).map { user -> user }
    }

    override suspend fun getUser(): Result<User> {
        val userId = appSettingsRepository.userIdFlow.first()
        return safeApiCall(
            method = {userApi.getUser(userId)},
            mapper = {dto -> dto.toDomain()}
        ).map { user -> user }
    }

    override suspend fun getNotificationActive(): Boolean {
        return appSettingsRepository.notificationActiveFlow.first()
    }

    override suspend fun setNotificationActive(value: Boolean) {
        appSettingsRepository.setNotificationActive(value)
    }

    override suspend fun logout() {
        tokenManager.setToken("")
        appSettingsRepository.setUserId("")
        appSettingsRepository.setBiometryUsed(false)
        appSettingsRepository.setPinCode("")
    }


}