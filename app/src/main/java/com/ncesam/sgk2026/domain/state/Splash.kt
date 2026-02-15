package com.ncesam.sgk2026.domain.state

sealed interface SplashEffect {
    object GoToLogin: SplashEffect
    object GoToMain: SplashEffect
}