package com.ncesam.sgk2026.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.ncesam.sgk2026.domain.repository.BiometricManager as DomainBiometricManager

class BiometricManagerImpl(private val activity: FragmentActivity) : DomainBiometricManager {
    @RequiresPermission(Manifest.permission.USE_BIOMETRIC)
    override fun authenticate(
        title: String, description: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    ) {
        val biometricManager = BiometricManager.from(activity)
        val executor = ContextCompat.getMainExecutor(activity)
        val canAuthentication =
            biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (canAuthentication != BiometricManager.BIOMETRIC_SUCCESS) {
            onError(Exception("Not allowed Biometric"))
            return
        }
        val prompt =
            BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    onError(Exception("Code: $errorCode. Error: $errString"))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onSuccess()
                }
            })
        val promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle(
            title
        ).setDescription(description).setNegativeButtonText("Отмена").build()

        prompt.authenticate(promptInfo)
    }

}