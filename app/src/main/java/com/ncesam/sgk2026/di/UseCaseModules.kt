package com.ncesam.sgk2026.di

import org.koin.dsl.module


val useCaseModules = module {
    factory<LoginUseCase> { LoginUseCase(get()) }
    factory<RegisterUseCase> { RegisterUseCase(get()) }
    factory<GetAllHotelUseCase> { GetAllHotelUseCase(get(), get()) }
}