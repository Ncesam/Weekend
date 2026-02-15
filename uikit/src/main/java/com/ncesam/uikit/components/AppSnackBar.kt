package com.ncesam.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider


@Composable
fun AppSnackBar(msg: String = "", onClick: () -> Unit) {

    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = Modifier.shadow(5.dp, shape)
            .width(380.dp)
            .height(120.dp)
            .background(colors.white, shape)
    ) {
        Icon(
            painter = AppTheme.icons.CrossRounded,
            tint = colors.icons,
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .size(24.dp)
                .align(Alignment.TopEnd)
                .clickable { onClick() }
        )
        BasicText(
            text = msg,
            style = typography.h2SemiBold,
            maxLines = 2,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .align(
                    Alignment.Center
                )
        )
    }

}


@Preview
@Composable
fun PreviewAppSnackBar() {
    AppThemeProvider {
        AppSnackBar("привет") { }
    }
}