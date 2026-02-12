package com.ncesam.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider


@Composable
fun AppSelect(value: String = "", placeholder: String = "", onClick: () -> Unit) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val shape = RoundedCornerShape(5.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.inputBackground, shape)
            .border(1.dp, colors.inputStroke, shape)
            .padding(14.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BasicText(
            text = value.ifBlank { placeholder },
            style = typography.h3Regular,
            color = { if (value.isBlank()) colors.caption else colors.black })
        Icon(
            painter = AppTheme.icons.ArrowBottom,
            tint = colors.description,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
fun PreviewAppSelect() {
    AppThemeProvider {
        Column {
            AppSelect("", "Пол") { }

            AppSelect("Мужской", "Пол") { }
        }
    }
}
