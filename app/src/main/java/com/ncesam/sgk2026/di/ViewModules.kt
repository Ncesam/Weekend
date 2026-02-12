package com.ncesam.sgk2026.di

import org.koin.core.context.GlobalContext.get
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModules = module {
    viewModel<LoginViewModel> { LoginViewModel(get()) }
    viewModel<RegisterViewModel> { RegisterViewModel(get())}
    viewModel<MainViewModel> { MainViewModel(get())}
    viewModel<ShopCartViewModel> { ShopCartViewModel(get())}
    viewModel<ProfileViewModel> { ProfileViewModel(get())}
}