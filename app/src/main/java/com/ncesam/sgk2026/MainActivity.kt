package com.ncesam.sgk2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import com.ncesam.sgk2026.presentation.biometric.BiometricProvider
import com.ncesam.sgk2026.presentation.navigation.MainNavigator
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(1))

        setContent {
            AppThemeProvider {
                BiometricProvider {
                    ScreenProvider {
                        MainNavigator()
                    }
                }

            }
        }

    }
}

