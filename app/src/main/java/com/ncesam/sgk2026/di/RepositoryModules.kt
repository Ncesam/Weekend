package com.ncesam.sgk2026.di

import com.ncesam.sgk2026.data.repository.AppSettingsRepositoryImpl
import com.ncesam.sgk2026.data.repository.AuthRepositoryImpl
import com.ncesam.sgk2026.data.repository.BookingRepositoryImpl
import com.ncesam.sgk2026.data.repository.HotelRepositoryImpl
import com.ncesam.sgk2026.data.repository.ShopCartRepositoryImpl
import com.ncesam.sgk2026.data.repository.TokenManagerImpl
import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import com.ncesam.sgk2026.domain.repository.AuthRepository
import com.ncesam.sgk2026.domain.repository.BookingRepository
import com.ncesam.sgk2026.domain.repository.HotelRepository
import com.ncesam.sgk2026.domain.repository.ShopCartRepository
import com.ncesam.sgk2026.domain.repository.TokenManager
import org.koin.dsl.module


val repositoryModules = module {
    single<HotelRepository> { HotelRepositoryImpl(get(), get()) }
    single<BookingRepository> { BookingRepositoryImpl(get(), get(), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<TokenManager> { TokenManagerImpl(get(), get()) }
    single<ShopCartRepository> { ShopCartRepositoryImpl(get(), get(), get()) }
    single<AppSettingsRepository> { AppSettingsRepositoryImpl(get()) }
}