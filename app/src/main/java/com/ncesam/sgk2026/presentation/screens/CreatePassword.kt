package com.ncesam.sgk2026.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.RegistrationEffect
import com.ncesam.sgk2026.domain.state.RegistrationEvent
import com.ncesam.sgk2026.domain.state.RegistrationInputState
import com.ncesam.sgk2026.domain.state.RegistrationState
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.RegistrationViewModel
import com.ncesam.uikit.components.AppButton
import com.ncesam.uikit.components.AppButtonStyle
import com.ncesam.uikit.components.AppInput
import com.ncesam.uikit.components.AppInputType
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch


@Composable
fun CreatePasswordScreen(viewModel: RegistrationViewModel) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val bottomTabs = AppNavigation.bottomTabs
    val navigator = AppNavigation.navigator

    bottomTabs.hide()

    val showSnackBar = ScreenProvider.showSnackBar



    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RegistrationEffect.ShowSnackBar -> {
                    showSnackBar(effect.msg)
                }

                RegistrationEffect.GoToLogin -> {
                    navigator.navigate(AppRoute.Login, clearToRoute = AppRoute.Login)
                }


                RegistrationEffect.GoToPassword -> {
                    navigator.navigate(AppRoute.CreatePassword)
                }

            }
        }
    }
    CreatePasswordContent(state) { event -> scope.launch { viewModel.onEvent(event) } }
}


@Composable
fun CreatePasswordContent(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    var inputFocused by remember { mutableStateOf(RegistrationInputState()) }
    val colors = AppTheme.colors
    val typography = AppTheme.typography
    val verticalScrollState = rememberScrollState()

    val shape = RoundedCornerShape(15.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(100.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .padding(top = 60.dp)
            .padding(horizontal = 20.dp)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(verticalScrollState)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(23.dp)
        ) {
            BasicText(text = "Создание пароля", style = typography.h1ExtraBold)
            BasicText(
                text = "Введите новый пароль",
                style = typography.captionRegular.copy(textAlign = TextAlign.Center)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppInput(
                type = AppInputType.Password,
                visiblePassword = inputFocused.passwordVisibility,
                focused = inputFocused.passwordFocused,
                helperText = "Новый Пароль",
                value = state.password,
                placeholder = "*********",
                errorText = state.passwordError,
                { text -> onEvent(RegistrationEvent.PasswordChanged(text)) },
                {
                    inputFocused =
                        inputFocused.copy(passwordVisibility = !inputFocused.passwordVisibility)
                }
            ) { focusState ->
                inputFocused = inputFocused.copy(passwordFocused = focusState.isFocused)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, shape)
                    .background(colors.white, shape)
                    .border(1.dp, colors.inputStroke, shape)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicText("Требования к паролю:", style = typography.captionSemiBold)
                BasicText(
                    "Минимум 8 символов",
                    style = typography.caption2Regular,
                    color = { if (state.password.isBlank()) colors.black else if (state.passwordRules.hasMinLength) colors.success else colors.error })
                BasicText(
                    "Заглавная буква",
                    style = typography.caption2Regular,
                    color = { if (state.password.isBlank()) colors.black else if (state.passwordRules.hasUpper) colors.success else colors.error })
                BasicText(
                    "Строчная буква",
                    style = typography.caption2Regular,
                    color = { if (state.password.isBlank()) colors.black else if (state.passwordRules.hasLower) colors.success else colors.error })
                BasicText(
                    "Цифра",
                    style = typography.caption2Regular,
                    color = { if (state.password.isBlank()) colors.black else if (state.passwordRules.hasDigits) colors.success else colors.error })
                BasicText(
                    """Спецсимвол (!@#\$)""",
                    style = typography.caption2Regular,
                    color = { if (state.password.isBlank()) colors.black else if (state.passwordRules.hasSymbol) colors.success else colors.error })

            }
            AppInput(
                type = AppInputType.Password,
                visiblePassword = inputFocused.confirmPasswordVisibility,
                focused = inputFocused.confirmPasswordFocused,
                helperText = "Повторите пароль",
                value = state.confirmPassword,
                placeholder = "*********",
                errorText = null,
                { text -> onEvent(RegistrationEvent.ConfirmPasswordChanged(text)) },
                {
                    inputFocused =
                        inputFocused.copy(confirmPasswordVisibility = !inputFocused.confirmPasswordVisibility)
                }
            ) { focusState ->
                inputFocused = inputFocused.copy(confirmPasswordFocused = focusState.isFocused)
            }
            AppButton(
                style = if (state.passwordRules.passed) AppButtonStyle.Accent else AppButtonStyle.Inactive,
                content = "Сохранить"
            ) {
                onEvent(RegistrationEvent.GoToLogin)
            }
        }

    }


}


@Preview
@Composable
fun PreviewCreatePassword() {
    val state = RegistrationState()
    AppThemeProvider {
        ScreenProvider {
            CreatePasswordContent(state) { }
        }
    }
}