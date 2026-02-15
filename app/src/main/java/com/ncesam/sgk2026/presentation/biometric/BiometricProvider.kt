package com.ncesam.sgk2026.presentation.biometric

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.ncesam.sgk2026.data.repository.BiometricManagerImpl
import com.ncesam.sgk2026.domain.repository.BiometricManager as DomainBiometricManager


val LocalBiometricManager = staticCompositionLocalOf<DomainBiometricManager> {
    error("Wrap your app BiometricProvider")
}

@Composable
fun BiometricProvider(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val biometricManager = BiometricManagerImpl(activity)

    CompositionLocalProvider(
        LocalBiometricManager provides biometricManager
    ) {
        content()
    }
}


object BiometricProvider {
    val biometricManager: DomainBiometricManager
        @Composable get() = LocalBiometricManager.current
}
