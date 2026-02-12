package com.ncesam.uikit.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
fun AppBottomTabItem(
    variant: AppBottomTabItemType,
    focused: Boolean = false,
    onClick: () -> Unit
) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography
    Box(contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.clickable { onClick() }, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(variant.icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = if (focused) colors.accent else colors.caption
            )
            BasicText(
                text = variant.title,
                style = typography.captionRegular,
                color = { if (focused) colors.accent else colors.caption })
        }
    }
}

@Preview
@Composable
fun PreviewAppBottomTabs() {
    AppThemeProvider {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            AppBottomTabItem(AppBottomTabItemType.Home, true) { }
            AppBottomTabItem(AppBottomTabItemType.Search, false) { }
            AppBottomTabItem(AppBottomTabItemType.Travels, false) { }
            AppBottomTabItem(AppBottomTabItemType.Profile, false) { }
        }
    }
}

enum class AppBottomTabItemType(@param:DrawableRes val icon: Int, val title: String) {
    Home(icon = R.drawable.home,  "Главная"),
    Search(icon = R.drawable.search, "Поиск"),
    Travels(icon = R.drawable.calendar, "Поездки"),
    Profile(icon = R.drawable.profile,  "Профиль")
}