package com.ncesam.sgk2026.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.MainEffect
import com.ncesam.sgk2026.domain.state.ShopCartEffect
import com.ncesam.sgk2026.domain.state.ShopCartEvent
import com.ncesam.sgk2026.domain.state.ShopCartState
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.ShopCartViewModel
import com.ncesam.uikit.components.AppButton
import com.ncesam.uikit.components.AppButtonBack
import com.ncesam.uikit.components.AppButtonStyle
import com.ncesam.uikit.components.CartAppCard
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ShopCartScreen(viewModel: ShopCartViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val navigator = AppNavigation.navigator
    val bottomTabs = AppNavigation.bottomTabs
    bottomTabs.show()
    val showSnackBar = ScreenProvider.showSnackBar

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ShopCartEffect.ShowSnackBar -> {
                    showSnackBar(effect.msg)
                }
                ShopCartEffect.Back -> {
                    navigator.back()
                }
                ShopCartEffect.GoToMain ->  {
                    navigator.navigate(AppRoute.Main, clearAll = true)
                }
            }
        }
    }

    ShopCartContent(state) { event -> scope.launch { viewModel.onEvent(event) } }
}

@Composable
fun ShopCartContent(state: ShopCartState, onEvent: (ShopCartEvent) -> Unit) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val shopCartColumnState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                AppButtonBack { onEvent(ShopCartEvent.Back) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BasicText("Корзина", style = typography.h1ExtraBold)
                    Icon(
                        painter = AppTheme.icons.Trash,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onEvent(ShopCartEvent.DeleteAll) },
                        contentDescription = null,
                        tint = colors.icons
                    )
                }
            }
            LazyColumn(state = shopCartColumnState, contentPadding = PaddingValues(vertical = 32.dp), verticalArrangement = Arrangement.spacedBy(32.dp)) {
                items(state.items) { item ->
                    CartAppCard(
                        item.title,
                        item.perItem,
                        item.quantity,
                        { onEvent(ShopCartEvent.Delete(item.id)) },
                        {
                            onEvent(
                                ShopCartEvent.Increment(item.id)
                            )
                        },
                        { onEvent(ShopCartEvent.Decrement(item.id)) })
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BasicText(text = "Сумма", style = typography.h2SemiBold)
                        BasicText(text = "${state.total} ₽", style = typography.h2SemiBold)
                    }
                }
            }

        }
        AppButton(
            style = AppButtonStyle.Accent,
            content = "Перейти к оформлению заказа"
        ) {
            onEvent(ShopCartEvent.Buy)
        }

    }
}

@Preview
@Composable
fun PreviewShopCartScreen() {
    val state = ShopCartState()
    AppThemeProvider {
        ScreenProvider {
            ShopCartContent(state) { }
        }
    }
}