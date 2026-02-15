package com.ncesam.sgk2026.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.PinCodeEffect
import com.ncesam.sgk2026.domain.state.PinCodeEvent
import com.ncesam.sgk2026.domain.state.PinCodeState
import com.ncesam.sgk2026.domain.state.RegistrationEvent
import com.ncesam.sgk2026.presentation.biometric.BiometricProvider
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.PinCodeViewModel
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PinCodeScreen(viewModel: PinCodeViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val bottomTabs = AppNavigation.bottomTabs
    val navigator = AppNavigation.navigator
    val biometricManager = BiometricProvider.biometricManager

    bottomTabs.hide()

    val showSnackBar = ScreenProvider.showSnackBar

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                PinCodeEffect.GoToMain -> {
                    navigator.navigate(AppRoute.Main, clearAll = true)
                }

                PinCodeEffect.BiometryClicked -> {
                    biometricManager.authenticate("Войти", "Войти с помощью биометрии", {
                        navigator.navigate(AppRoute.Main)
                    }, {
                        showSnackBar("Не удалось войти")
                    })
                }

                is PinCodeEffect.ShowSnackBar -> {
                    showSnackBar(effect.msg)
                }
            }
        }
    }

    PinCodeContent(state) { event -> scope.launch { viewModel.onEvent(event) } }
}


@Composable
fun PinCodeContent(state: PinCodeState, onEvent: (PinCodeEvent) -> Unit) {

    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val dotsRowState = rememberLazyListState()
    val buttonsGridState = rememberLazyGridState()

    val shape = CircleShape

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(60.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .padding(top = 100.dp)
            .padding(horizontal = 20.dp)
            .statusBarsPadding()
            .imePadding()
    ) {
        BasicText(text = "Вход", style = typography.h1ExtraBold)
        LazyRow(state = dotsRowState, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items((1..4).toList()) { index ->
                val modifier = Modifier
                    .size(16.dp)
                    .border(1.dp, colors.accent, shape)
                Box(
                    modifier = if (state.pinCode.length >= index) modifier.background(
                        colors.accent,
                        shape
                    ) else modifier
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = buttonsGridState,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(items = (1..9).toList()) { index ->
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .background(
                            color = if (isPressed) colors.accent else colors.inputStroke,
                            CircleShape
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onEvent(PinCodeEvent.PinCodeAdd(index.toString()))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = index.toString(),
                        style = typography.h1SemiBold.copy(textAlign = TextAlign.Center)
                    )
                }
            }
            item {
                if(state.biometryActive){
                    var isPressed by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .background(
                                color = if (isPressed) colors.accent else colors.inputStroke,
                                CircleShape
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(onTap = {
                                    try {
                                        isPressed = true
                                        onEvent(PinCodeEvent.BiometryAuthClicked)
                                    } finally {
                                        isPressed = false
                                    }
                                })
                            }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = AppTheme.icons.Biometry,
                            contentDescription = null
                        )
                    }
                } else Spacer(modifier = Modifier)}
            item {
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .background(
                            color = if (isPressed) colors.accent else colors.inputStroke,
                            CircleShape
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onEvent(PinCodeEvent.PinCodeAdd( 0.toString()))
                        }, contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = 0.toString(),
                        style = typography.h1SemiBold.copy(textAlign = TextAlign.Center)
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable {
                            onEvent(PinCodeEvent.PinCodeDelete)
                        }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = AppTheme.icons.Delete,
                        tint = colors.black,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewPinCodeScreen() {
    val state = PinCodeState(false)
    AppThemeProvider {
        ScreenProvider {
                PinCodeContent(state) { }
        }
    }
}