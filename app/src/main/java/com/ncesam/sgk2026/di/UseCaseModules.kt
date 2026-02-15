package com.ncesam.sgk2026.di

import com.ncesam.sgk2026.domain.useCase.GetAllHotelUseCase
import com.ncesam.sgk2026.domain.useCase.LoginUseCase
import com.ncesam.sgk2026.domain.useCase.RegistrationUseCase
import org.koin.dsl.module


val useCaseModules = module {
    factory<LoginUseCase> { LoginUseCase(get(), get()) }
    factory<RegistrationUseCase> { RegistrationUseCase(get()) }
    factory<GetAllHotelUseCase> { GetAllHotelUseCase(get()) }
}