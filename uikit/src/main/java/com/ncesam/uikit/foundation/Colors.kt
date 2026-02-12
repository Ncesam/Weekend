package com.ncesam.uikit.foundation

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt


data class AppColors(
    val accent: Color,
    val error: Color,
    val accentInactive: Color,
    val black: Color,
    val white: Color,
    val success: Color,
    val inputBackground: Color,
    val inputStroke: Color,
    val inputIcons: Color,
    val placeholder: Color,
    val description: Color,
    val cardStroke: Color,
    val caption: Color,
    val icons: Color,
    val divider: Color,
)

private fun hex(color: String): Color = Color(color.toColorInt())


val LightColorPalette = AppColors(
    accent = hex("#2074F2"),
    accentInactive = hex("#C5D2FF"),
    black = hex("#2D2C2C"),
    white = hex("#F7F7F7"),
    error = hex("#FF4646"),
    success = hex("#00B412"),
    inputBackground = hex("#F5F5F9"),
    inputStroke = hex("#E6E6E6"),
    inputIcons = hex("#BFC7D1"),
    placeholder = hex("#98989A"),
    description = hex("#8787A1"),
    cardStroke = hex("#F2F2F2"),
    caption = hex("#939396"),
    icons = hex("#B8C1CC"),
    divider = hex("#F4F4F4")
)