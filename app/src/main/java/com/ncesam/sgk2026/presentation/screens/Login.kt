package com.ncesam.sgk2026.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.LoginEffect
import com.ncesam.sgk2026.domain.state.LoginEvent
import com.ncesam.sgk2026.domain.state.LoginInputState
import com.ncesam.sgk2026.domain.state.LoginState
import com.ncesam.sgk2026.domain.state.RegistrationInputState
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.LoginViewModel
import com.ncesam.uikit.components.AppButton
import com.ncesam.uikit.components.AppButtonOAuth
import com.ncesam.uikit.components.AppButtonOAuthType
import com.ncesam.uikit.components.AppButtonStyle
import com.ncesam.uikit.components.AppInput
import com.ncesam.uikit.components.AppInputType
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val navigator = AppNavigation.navigator
    val bottomTabs = AppNavigation.bottomTabs
    bottomTabs.hide()

    val showSnackBar = ScreenProvider.showSnackBar

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginEffect.ShowSnackBar -> {
                    showSnackBar(effect.msg)
                }

                LoginEffect.GoToCreatePinCode -> {
                    navigator.navigate(AppRoute.CreatePinCode)
                }

                LoginEffect.GoToRegistration -> {
                    navigator.navigate(AppRoute.CreateProfile)
                }
            }
        }
    }

    LoginContent(state) { event -> scope.launch { viewModel.onEvent(event) } }
}


@Composable
fun LoginContent(state: LoginState, onEvent: (LoginEvent) -> Unit) {
    var inputFocused by remember { mutableStateOf(RegistrationInputState()) }

    val colors = AppTheme.colors
    val typography = AppTheme.typography


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .padding(horizontal = 20.dp)
            .padding(top = 60.dp)
            .imePadding()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(60.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BasicText(text = "Добро пожаловать!", style = typography.h1ExtraBold)
            BasicText(
                text = "Войдите, чтобы пользоваться функциями приложения",
                style = typography.textMedium.copy(textAlign = TextAlign.Center)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppInput(
                focused = inputFocused.emailFocused,
                helperText = "Вход по E-mail",
                value = state.email,
                errorText = state.emailError,
                onChangeText = { value ->
                    onEvent(LoginEvent.EmailChanged(value))
                },
                placeholder = "",
                onClickVisibility = {})
            { focusState ->
                inputFocused = inputFocused.copy(emailFocused = focusState.isFocused)
            }
            AppInput(
                type = AppInputType.Password,
                visiblePassword = inputFocused.passwordVisibility,
                focused = inputFocused.passwordFocused,
                helperText = "Пароль",
                value = state.password,
                errorText = state.passwordError,
                placeholder = "",
                onChangeText = { value ->
                    onEvent(LoginEvent.PasswordChanged(value))
                },
                onClickVisibility = {
                    inputFocused =
                        inputFocused.copy(passwordVisibility = !inputFocused.passwordVisibility)
                })
            { focusState ->
                inputFocused = inputFocused.copy(passwordFocused = focusState.isFocused)
            }
            AppButton(
                style = if (!state.isActiveButton) AppButtonStyle.Inactive else AppButtonStyle.Accent,
                content = "Далее"
            ) {
                onEvent(LoginEvent.LoginClicked)
            }
            BasicText(
                text = "Зарегистрироваться",
                style = typography.textMedium,
                color = { colors.accent },
                modifier = Modifier.clickable { onEvent(LoginEvent.RegisterClicked) })
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicText(
                text = "Или продолжите с помощью",
                style = typography.textMedium,
                color = { colors.caption })
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppButtonOAuth(AppButtonOAuthType.VK) { onEvent(LoginEvent.OAuthClicked) }
                AppButtonOAuth(AppButtonOAuthType.Yandex) { onEvent(LoginEvent.OAuthClicked) }
            }
        }
    }
}


@Preview
@Composable
fun PreviewLoginScreen() {
    val state = LoginState()
    AppThemeProvider {
        ScreenProvider {
            LoginContent(state) { }
        }
    }
}