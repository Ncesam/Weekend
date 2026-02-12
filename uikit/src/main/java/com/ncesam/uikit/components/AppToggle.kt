package com.ncesam.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider


@Composable
fun AppToggle(
    enabled: Boolean = false,
    onClick: () -> Unit,
) {

    val colors = AppTheme.colors
    val shape = RoundedCornerShape(50)


    Box(
        modifier = Modifier
            .background(if (enabled) colors.accent else colors.white, shape)
            .padding(
                start = if (enabled) 22.dp else 2.dp,
                top = 2.dp,
                bottom = 2.dp,
                end = if (!enabled) 22.dp else 2.dp
            )
            .clickable { onClick() }) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .shadow(12.dp, shape = CircleShape)
                .background(colors.white, CircleShape)
        )
    }
}

@Preview
@Composable
fun PreviewAppToggle() {
    AppThemeProvider {
        Column {
            AppToggle { }
            AppToggle(true) { }

        }
    }
}
