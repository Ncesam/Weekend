package com.ncesam.sgk2026.di

import com.ncesam.sgk2026.presentation.viewModel.BookingViewModel
import com.ncesam.sgk2026.presentation.viewModel.CreatePinCodeViewModel
import com.ncesam.sgk2026.presentation.viewModel.LoginViewModel
import com.ncesam.sgk2026.presentation.viewModel.MainViewModel
import com.ncesam.sgk2026.presentation.viewModel.PinCodeViewModel
import com.ncesam.sgk2026.presentation.viewModel.RegistrationViewModel
import com.ncesam.sgk2026.presentation.viewModel.ShopCartViewModel
import com.ncesam.sgk2026.presentation.viewModel.SplashViewModel
import com.ncesam.sgk2026.presentation.viewModel.TravelsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModules = module {
    viewModel<LoginViewModel> { LoginViewModel(get()) }
    viewModel<RegistrationViewModel> { RegistrationViewModel(get()) }
    viewModel<SplashViewModel> { SplashViewModel(get(), get()) }
    viewModel<PinCodeViewModel> { PinCodeViewModel(get()) }
    viewModel<MainViewModel> { MainViewModel(get(), get()) }
    viewModel<CreatePinCodeViewModel> { CreatePinCodeViewModel(get()) }
    viewModel<BookingViewModel> { BookingViewModel(get(), get(), get(), get()) }
    viewModel<ShopCartViewModel> { ShopCartViewModel(get())}
    viewModel<TravelsViewModel> { TravelsViewModel(get(), get())}
//    viewModel<ProfileViewModel> { ProfileViewModel(get())}
}