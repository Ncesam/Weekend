package com.ncesam.sgk2026.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.prefs.Preferences

const val BASE_URL = "http://localhost:8090/"

val json = Json {
    ignoreUnknownKeys = true
}

val networkModules = module {
    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            addInterceptor(logging)
        }.build()
    }

    single<Retrofit> {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())).client(get()).build()
    }

}