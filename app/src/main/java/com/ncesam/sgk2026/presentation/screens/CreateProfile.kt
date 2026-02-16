package com.ncesam.sgk2026.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.RegistrationEffect
import com.ncesam.sgk2026.domain.state.RegistrationEvent
import com.ncesam.sgk2026.domain.state.RegistrationInputState
import com.ncesam.sgk2026.domain.state.RegistrationState
import com.ncesam.sgk2026.presentation.biometric.BiometricProvider
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.RegistrationViewModel
import com.ncesam.uikit.components.AppBottomSheet
import com.ncesam.uikit.components.AppButton
import com.ncesam.uikit.components.AppButtonStyle
import com.ncesam.uikit.components.AppInput
import com.ncesam.uikit.components.AppSelect
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch

@Composable
fun CreateProfileScreen(viewModel: RegistrationViewModel) {
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

    CreateProfileContent(state) { event -> scope.launch { viewModel.onEvent(event) } }
}

@Composable
fun CreateProfileContent(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    var inputFocused by remember { mutableStateOf(RegistrationInputState()) }
    var sheetExpanded by remember { mutableStateOf(false) }
    val colors = AppTheme.colors
    val typography = AppTheme.typography
    val verticalScrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .padding(top = 30.dp)
            .padding(horizontal = 20.dp)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(verticalScrollState)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(44.dp)
        ) {
            BasicText(text = "Создание Профиля", style = typography.h1ExtraBold)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicText(
                    text = "Без профиля вы не сможете бронировать туры",
                    style = typography.captionRegular
                )
                BasicText(
                    text = "В профиле будут храниться результаты поиска и забронированные туры",
                    style = typography.captionRegular.copy(textAlign = TextAlign.Center)
                )
            }

        }
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppInput(
                focused = inputFocused.firstNameFocused,
                value = state.firstName,
                placeholder = "Имя",
                onChangeText = { text -> onEvent(RegistrationEvent.FirstNameChanged(text)) },
                onClickVisibility = {}
            ) { focusState ->
                inputFocused = inputFocused.copy(firstNameFocused = focusState.isFocused)
            }
            AppInput(
                focused = inputFocused.lastNameFocused,
                value = state.lastName,
                placeholder = "Фамилия",
                onChangeText = { text -> onEvent(RegistrationEvent.LastNameChanged(text)) },
                onClickVisibility = {}
            ) { focusState ->
                inputFocused = inputFocused.copy(lastNameFocused = focusState.isFocused)
            }
            AppInput(
                focused = inputFocused.fatherNameFocused,
                value = state.fatherName,
                placeholder = "Отчество",
                onChangeText = { text -> onEvent(RegistrationEvent.FatherNameChanged(text)) },
                onClickVisibility = {}
            ) { focusState ->
                inputFocused = inputFocused.copy(fatherNameFocused = focusState.isFocused)
            }
            AppInput(
                focused = inputFocused.bornFocused,
                value = state.born,
                placeholder = "Дата Рождения",
                errorText = state.bornError,
                onChangeText = { text -> onEvent(RegistrationEvent.BornChanged(text)) },
                onClickVisibility = {}
            ) { focusState -> inputFocused = inputFocused.copy(bornFocused = focusState.isFocused) }
            AppSelect(
                value = state.gender, placeholder = "Пол"
            ) {
                sheetExpanded = true
            }
            AppInput(
                focused = inputFocused.emailFocused,
                value = state.email,
                placeholder = "Почта",
                errorText = state.emailError,
                onChangeText = { text -> onEvent(RegistrationEvent.EmailChanged(text)) },
                onClickVisibility = {}
            ) { focusState ->
                inputFocused = inputFocused.copy(emailFocused = focusState.isFocused)
            }

            if (sheetExpanded) {
                AppBottomSheet(
                    onDismiss = { sheetExpanded = false },
                    expanded = true,
                    name = "Выберете пол"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            BasicText(
                                text = "Мужской",
                                style = typography.h3SemiBold,
                                modifier = Modifier.clickable {
                                    onEvent(RegistrationEvent.GenderChanged("Мужской"))
                                    sheetExpanded = false
                                })
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            BasicText(
                                text = "Женский",
                                style = typography.h3SemiBold,
                                modifier = Modifier.clickable {
                                    onEvent(RegistrationEvent.GenderChanged("Женский"))
                                    sheetExpanded = false
                                })
                        }
                    }
                }
            }
            AppButton(AppButtonStyle.Accent, content = "Далее") {
                onEvent(RegistrationEvent.GoToPassword)
            }
        }

    }

}


@Preview
@Composable
fun PreviewCreateProfileScreen() {
    val state = RegistrationState()
    AppThemeProvider {
        ScreenProvider {
            CreateProfileContent(state) { }
        }
    }
}