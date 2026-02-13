package com.ncesam.sgk2026.domain.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {

    @Serializable
    object Splash: AppRoute

    @Serializable
    object MainGraph: AppRoute

    @Serializable
    object AuthGraph: AppRoute

    @Serializable
    object Login: AppRoute

    @Serializable
    object CreateProfile: AppRoute

    @Serializable
    object CreatePassword: AppRoute

    @Serializable
    object CreatePinCode: AppRoute


    @Serializable
    object Profile: AppRoute

    @Serializable
    object PinCode: AppRoute
}