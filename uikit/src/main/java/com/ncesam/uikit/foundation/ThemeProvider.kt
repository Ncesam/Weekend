package com.ncesam.uikit.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


val LocalAppTypography = staticCompositionLocalOf {
    AppTypography
}

val LocalAppColors = staticCompositionLocalOf {
    LightColorPalette
}


@Composable
fun AppThemeProvider(content: @Composable () -> Unit) {

    CompositionLocalProvider(
        LocalAppColors provides LightColorPalette,
        LocalAppTypography provides AppTypography
    ) {
        content()
    }
}


object AppTheme {
    val colors: AppColors
       @Composable get() =  LocalAppColors.current
    val typography: AppTypographySchema
        @Composable get() = LocalAppTypography.current
    val icons = LightAppIcons
}