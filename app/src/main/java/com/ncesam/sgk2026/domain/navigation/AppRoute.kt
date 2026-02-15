package com.ncesam.sgk2026.domain.navigation

import com.ncesam.sgk2026.domain.models.Hotel
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

    @Serializable
    object Main: AppRoute

    @Serializable
    data class Booking(val hotelId: String): AppRoute

    @Serializable
    data class Search(val value: String): AppRoute

    @Serializable
    object ShopCart: AppRoute

    @Serializable
    object Travels: AppRoute
}