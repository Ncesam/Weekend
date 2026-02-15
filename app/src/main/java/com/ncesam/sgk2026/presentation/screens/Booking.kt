package com.ncesam.sgk2026.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.BookingEffect
import com.ncesam.sgk2026.domain.state.BookingEvent
import com.ncesam.sgk2026.domain.state.BookingInputState
import com.ncesam.sgk2026.domain.state.BookingState
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.BookingViewModel
import com.ncesam.uikit.components.AppButton
import com.ncesam.uikit.components.AppButtonStyle
import com.ncesam.uikit.components.AppInput
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun BookingScreen(viewModel: BookingViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val navigator = AppNavigation.navigator
    val bottomTabs = AppNavigation.bottomTabs
    bottomTabs.show()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                BookingEffect.GoToMain -> {
                    navigator.navigate(AppRoute.Main, AppRoute.Main)
                }
            }
        }
    }

    BookingContent(state) { event -> scope.launch { viewModel.onEvent(event) } }

}

@Composable
fun BookingContent(state: BookingState, onEvent: (event: BookingEvent) -> Unit) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography

    var inputFocused by remember { mutableStateOf(BookingInputState()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(top = 30.dp, bottom=20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicText(text = "Бронирование", style = typography.h2SemiBold)
            AppInput(
                placeholder = "--.--.----",
                focused = inputFocused.dateFromFocused,
                helperText = "Дата заезда",
                errorText = state.dateFromError,
                value = state.dateFrom,
                onChangeText = { text -> onEvent(BookingEvent.DateFromChanged(text)) },
                onClickVisibility = {}) { focusState ->
                inputFocused = inputFocused.copy(dateFromFocused = focusState.isFocused)
            }
            AppInput(
                placeholder = "--.--.----",
                focused = inputFocused.dateToFocused,
                helperText = "Дата выезда",
                errorText = state.dateToError,
                value = state.dateTo,
                onChangeText = { text -> onEvent(BookingEvent.DateToChanged(text)) },
                onClickVisibility = {}) { focusState ->
                inputFocused = inputFocused.copy(dateToFocused = focusState.isFocused)
            }
            AppInput(
                placeholder = "+7(---)--- -- --",
                focused = inputFocused.phoneFocused,
                helperText = "Номер телефона",
                errorText = state.phoneError,
                value = state.phone,
                onChangeText = { text -> onEvent(BookingEvent.PhoneChanged(text)) },
                onClickVisibility = {}) { focusState ->
                inputFocused = inputFocused.copy(phoneFocused = focusState.isFocused)
            }
            AppInput(
                placeholder = "Введите имя",
                focused = inputFocused.nameBookedFocused,
                helperText = "Имя",
                errorText = null,
                value = state.nameBooked,
                onChangeText = { text -> onEvent(BookingEvent.NameBookedChanged(text)) },
                onClickVisibility = {}) { focusState ->
                inputFocused = inputFocused.copy(nameBookedFocused = focusState.isFocused)
            }
        }
        AppButton(
            style = AppButtonStyle.Accent, content = "Подтвердить"
        ) {
            onEvent(BookingEvent.AddToCart)
        }
    }

}

@Preview
@Composable
fun PreviewBookingScreen() {
    val state = BookingState()
    AppThemeProvider {
        ScreenProvider {
            BookingContent(state) { }
        }
    }
}