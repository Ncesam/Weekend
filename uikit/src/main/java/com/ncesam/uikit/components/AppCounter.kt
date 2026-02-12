package com.ncesam.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider

@Composable
fun AppCounter(
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    quantity: Int = 1,
    minValue: Int = 1,
) {

    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val condition = quantity <= minValue

    Row(horizontalArrangement = Arrangement.spacedBy(40.dp), verticalAlignment = Alignment.CenterVertically) {
        BasicText(text = "$quantity человек", style = typography.textRegular)
        Row(
            modifier = Modifier.background(colors.inputBackground, RoundedCornerShape(8.dp)).padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = AppTheme.icons.Minus,
                tint = { if (condition) colors.icons else colors.caption },
                modifier = Modifier
                    .size(20.dp)
                    .clickable(enabled = !condition, onClick = { onDecrement() }),
                contentDescription = null
            )
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(16.dp)
                    .background(colors.inputStroke)
            )
            Icon(
                painter = AppTheme.icons.Plus,
                tint = colors.caption,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onIncrement() },
                contentDescription = null
            )
        }
    }
}


@Preview
@Composable
fun PreviewAppCounter() {
    AppThemeProvider {
        AppCounter({}, {}, 10, 1)
    }
}