package com.ncesam.uikit.foundation

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class AppTypographySchema(
    val h1SemiBold: TextStyle,
    val h1ExtraBold: TextStyle,
    val h2Regular: TextStyle,
    val h2SemiBold: TextStyle,
    val h2ExtraBold: TextStyle,
    val h3Regular: TextStyle,
    val h3Medium: TextStyle,
    val h3SemiBold: TextStyle,
    val headlineRegular: TextStyle,
    val headlineMedium: TextStyle,
    val textRegular: TextStyle,
    val textMedium: TextStyle,
    val captionRegular: TextStyle,
    val captionSemiBold: TextStyle,
    val caption2Regular: TextStyle,
    val caption2Bold: TextStyle,
)

val AppTypography = AppTypographySchema(
    h1SemiBold = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp),
    h1ExtraBold = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 28.sp
    ),
    h2Regular = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal, lineHeight = 28.sp),
    h2SemiBold = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp),
    h2ExtraBold = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 28.sp
    ),
    h3Regular = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Normal, lineHeight = 24.sp),
    h3Medium = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Medium, lineHeight = 24.sp),
    h3SemiBold = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp),
    headlineRegular = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    headlineMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp
    ),
    textRegular = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal, lineHeight = 20.sp),
    textMedium = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium, lineHeight = 20.sp),
    captionRegular = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    captionSemiBold = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp
    ),
    caption2Regular = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),
    caption2Bold = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, lineHeight = 16.sp),
)