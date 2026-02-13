package com.ncesam.uikit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.uikit.R
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider


@Composable
fun AppButton(
    style: AppButtonStyle = AppButtonStyle.Default,
    size: AppButtonSize = AppButtonSize.Big,
    content: String = "Пример",
    onClick: () -> Unit,
) {

    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val shape = RoundedCornerShape(5.dp)

    val textStyle = when (size) {
        AppButtonSize.Big -> typography.h3SemiBold
        AppButtonSize.Small -> typography.captionSemiBold
        AppButtonSize.Chip -> typography.textMedium
    }
    var modifier = when (style) {
        AppButtonStyle.Accent -> {
            Modifier.background(colors.accent, shape)
        }

        AppButtonStyle.Inactive -> {
            Modifier.background(colors.accentInactive, shape)
        }

        AppButtonStyle.Default -> {
            Modifier.background(colors.inputBackground, shape)
        }

        AppButtonStyle.Stroked -> {
            Modifier
                .background(colors.white, shape)
                .border(1.dp, colors.accent, shape)
        }
    }
    modifier = when (size) {
        AppButtonSize.Chip -> {
            modifier
                .width(130.dp)
                .padding(vertical = 14.dp)
        }

        AppButtonSize.Big -> {
            modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        }

        AppButtonSize.Small -> {
            modifier
                .width(100.dp)
                .padding(vertical = 10.dp)
        }
    }

    Row(
        modifier = modifier.clickable(enabled = style != AppButtonStyle.Inactive) { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(text = content, style = textStyle, color = {
            when (style) {
                AppButtonStyle.Accent -> colors.white
                AppButtonStyle.Inactive -> colors.white
                AppButtonStyle.Stroked -> colors.accent
                AppButtonStyle.Default -> colors.black
            }
        })
    }
}


@Composable
fun AppButtonCircle(onClick: () -> Unit) {
    val colors = AppTheme.colors
    Box(
        modifier = Modifier
            .background(colors.inputBackground, RoundedCornerShape(100))
            .padding(45.dp)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = AppTheme.icons.Plus,
            modifier = Modifier.size(20.dp),
            contentDescription = null,
            tint = colors.icons
        )
    }
}

@Composable
fun AppButtonBack(
    onClick: () -> Unit
) {
    val colors = AppTheme.colors

    Box(
        modifier = Modifier
            .background(colors.icons, RoundedCornerShape(10.dp))
            .padding(6.dp)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = AppTheme.icons.ArrowLeft,
            contentDescription = null,
            tint = colors.description,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun AppButtonFilter(
    onClick: () -> Unit
) {
    val colors = AppTheme.colors

    Box(
        modifier = Modifier
            .background(colors.icons, RoundedCornerShape(10.dp))
            .padding(14.dp)
            .clickable { onClick() }, contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = AppTheme.icons.Filter,
            contentDescription = null,
            tint = colors.description,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun AppButtonGoToCart(
    total: Int = 500,
    onClick: () -> Unit
) {

    val colors = AppTheme.colors
    val typography = AppTheme.typography


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.accent, RoundedCornerShape(5.dp))
            .padding(16.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = AppTheme.icons.ShopCart,
                tint = colors.white,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            BasicText(text = "Оплатить", style = typography.h2SemiBold, color = { colors.white })
        }
        BasicText(text = "$total ₽", style = typography.h2SemiBold, color = { colors.white })
    }
}

@Composable
fun AppButtonOAuth(
    type: AppButtonOAuthType = AppButtonOAuthType.VK,
    onClick: () -> Unit,
) {
    val colors = AppTheme.colors

    val icon = when (type) {
        AppButtonOAuthType.VK -> {
            painterResource(R.drawable.vk)
        }

        AppButtonOAuthType.Yandex -> painterResource(R.drawable.yandex)
    }
    Box(
        modifier = Modifier
            .background(colors.white, RoundedCornerShape(12.dp))
            .border(1.dp, colors.inputStroke, RoundedCornerShape(12.dp))
            .padding(19.dp)
            .clickable { onClick() }, contentAlignment = Alignment.Center) {
        Image(painter = icon, contentDescription = null, modifier = Modifier.size(32.dp))
    }
}

enum class AppButtonStyle {
    Accent,
    Inactive,
    Stroked,
    Default
}

enum class AppButtonSize {
    Big,
    Small,
    Chip,
}

enum class AppButtonOAuthType {
    VK,
    Yandex
}


@Preview
@Composable
fun PreviewAppButton() {
    AppThemeProvider {
        Column() {
            AppButton(style = AppButtonStyle.Accent) { }
            AppButton(style = AppButtonStyle.Stroked, size = AppButtonSize.Chip) { }
            AppButton(style = AppButtonStyle.Default, size = AppButtonSize.Small) { }
            AppButtonCircle {  }
            AppButtonGoToCart() { }
            AppButtonFilter {  }
            AppButtonBack {  }
            AppButtonOAuth() { }
        }

    }
}