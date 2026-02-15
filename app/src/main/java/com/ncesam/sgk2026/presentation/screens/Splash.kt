package com.ncesam.sgk2026.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.SplashEffect
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.SplashViewModel
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel = koinViewModel()) {
    val isLoading by viewModel.isLoading.collectAsState()

    val navigator = AppNavigation.navigator

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashEffect.GoToLogin -> {
                    navigator.navigate(AppRoute.AuthGraph, clearAll = true)
                }

                SplashEffect.GoToMain -> {
                    navigator.navigate(AppRoute.MainGraph, clearAll = true)
                }
            }
        }
    }

    if (isLoading) {
        SplashContent()
    }
}

@Composable
fun SplashContent() {
    val typography = AppTheme.typography
    val brush = Brush.linearGradient(
        listOf(Color("#003D8E".toColorInt()), Color("#A371FF".toColorInt())),
        start = Offset(Float.POSITIVE_INFINITY, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush),
        contentAlignment = Alignment.Center
    ) {
        BasicText("Travel", style = typography.h1ExtraBold.copy(lineHeight = 64.sp, fontSize = 40.sp), color = { Color.White })
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    AppThemeProvider {
        ScreenProvider {
            SplashContent()
        }
    }
}