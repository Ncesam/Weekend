package com.ncesam.uikit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.uikit.R
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider


@Composable
fun PrimaryAppCard(
    image: Painter = painterResource(R.drawable.test_image),
    title: String = "Отель Белград",
    price: Int = 300,
    quantity: Int = 0,
    added: Boolean = false,
    onClick: () -> Unit
) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val shape = RoundedCornerShape(15.dp)
    Column(
        modifier = Modifier
            .width(340.dp)
            .height(250.dp)
            .clip(shape)
            .background(colors.white)
            .border(1.dp, colors.inputStroke, shape)
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            BasicText(text = title, style = typography.headlineMedium)
            if (quantity > 0) {
                BasicText(text = "$quantity человек", style = typography.headlineRegular)
            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(text = "От $price ₽", style = typography.h3SemiBold)
                AppButton(
                    size = AppButtonSize.Small,
                    content = if (added) "Отменить" else "Выбрать",
                    style = if (added) AppButtonStyle.Stroked else AppButtonStyle.Accent
                ) { onClick() }
            }
        }
    }
}

@Composable
fun SmallAppCard(
    image: Painter = painterResource(R.drawable.test_image),
    title: String = "Отель Белград",
    onClick: () -> Unit
) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val shape = RoundedCornerShape(15.dp)
    Row(
        modifier = Modifier
            .width(230.dp)
            .height(140.dp)
            .clip(shape)
            .background(colors.white, shape)
            .clickable { onClick() }
            .border(1.dp, colors.inputStroke, shape),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1.2f)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.Center
            )
        }
        BasicText(text = title, style = typography.headlineMedium, modifier = Modifier.weight(1f))
    }
}


@Composable
fun CartAppCard(
    title: String = "Оте",
    price: Int = 300,
    quantity: Int = 1,
    onDelete: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography
    val shape = RoundedCornerShape(12.dp)

    Column(
        modifier = Modifier
            .width(340.dp)
            .shadow(2.dp, shape)
            .background(colors.white, shape)
            .border(1.dp, colors.divider, shape).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(54.dp),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            BasicText(text = title, style = typography.headlineMedium)
            Icon(
                painter = AppTheme.icons.Cross,
                tint = colors.description,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onDelete() }
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            BasicText(text = "$price ₽", style = typography.h3Medium)
            AppCounter(onIncrement, onDecrement, quantity, minValue = 1)
        }
    }
}

@Preview
@Composable
fun PreviewAppCard() {
    AppThemeProvider {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
//            PrimaryAppCard { }
//            PrimaryAppCard(quantity = 1) { }
//            PrimaryAppCard(added = true) { }
//            SmallAppCard {}
            CartAppCard(onDelete = {}, onDecrement = {}, onIncrement = {})
        }

    }
}