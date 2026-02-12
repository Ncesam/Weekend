package com.ncesam.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    onDismiss: () -> Unit,
    expanded: Boolean = false,
    name: String = "Пример",
    content: @Composable () -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    val colors = AppTheme.colors
    val typography = AppTheme.typography
    val shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

    if (expanded) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = colors.white,
            contentColor = colors.white,
            sheetGesturesEnabled = true,
            tonalElevation = 1.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        colors.white, shape
                    ), contentAlignment = Alignment.BottomCenter
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicText(text = name, style = typography.h2ExtraBold)
                        Icon(
                            painter = AppTheme.icons.CrossRounded,
                            tint = colors.icons,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp).clickable {onDismiss}
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(colors.divider)
                    )
                    content()
                }
            }
        }
    }
}


@Preview("BottomSheet", "")
@Composable
fun PreviewAppBottomSheet() {
    AppThemeProvider {
        AppBottomSheet({}, true) { }
    }
}