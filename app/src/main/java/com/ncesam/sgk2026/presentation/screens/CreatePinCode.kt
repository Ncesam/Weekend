import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.CreatePinCodeEffect
import com.ncesam.sgk2026.domain.state.CreatePinCodeEvent
import com.ncesam.sgk2026.domain.state.CreatePinCodeState
import com.ncesam.sgk2026.presentation.biometric.BiometricProvider
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.CreatePinCodeViewModel
import com.ncesam.uikit.components.AppButton
import com.ncesam.uikit.components.AppButtonSize
import com.ncesam.uikit.components.AppButtonStyle
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreatePinCodeScreen(viewModel: CreatePinCodeViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val bottomTabs = AppNavigation.bottomTabs
    val navigator = AppNavigation.navigator

    bottomTabs.hide()

    val biometricManager = BiometricProvider.biometricManager

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreatePinCodeEffect.ShowBiometric -> {
                    biometricManager.authenticate(
                        "Потвердить выбор",
                        "",
                        {
                            scope.launch { viewModel.onEvent(CreatePinCodeEvent.PinCodeCreate) }
                        },
                        { exception ->
                            scope.launch {
                                viewModel.onEvent(
                                    CreatePinCodeEvent.BiometryUsedChanged(false)
                                )
                                viewModel.onEvent(CreatePinCodeEvent.PinCodeCreate)
                            }
                        })
                }

                CreatePinCodeEffect.GoToMain -> {
                    navigator.navigate(AppRoute.Main, clearAll = true)
                }
            }
        }
    }
    CreatePinCodeContent(state) { event -> scope.launch { viewModel.onEvent(event) } }
}

@Composable
fun CreatePinCodeContent(state: CreatePinCodeState, onEvent: (CreatePinCodeEvent) -> Unit) {
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BasicText(text = "Создайте пароль", style = typography.h1ExtraBold)
            BasicText(
                text = "Для защиты ваших персональных данных",
                style = typography.captionRegular.copy(textAlign = TextAlign.Center)
            )
        }
        LazyRow(state = dotsRowState, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items((1..4).toList()) { index ->
                val modifier = Modifier
                    .size(16.dp)
                    .border(1.dp, colors.accent, shape)
                Box(
                    modifier = if (state.pincode.length >= index) modifier.background(
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
                            onEvent(CreatePinCodeEvent.PinCodeAdd(index.toString()))
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
                Spacer(modifier = Modifier)
            }
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
                            onEvent(CreatePinCodeEvent.PinCodeAdd(0.toString()))
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
                            onEvent(CreatePinCodeEvent.PinCodeDelete)
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
        if (state.biometryShow) {
            Popup(
                alignment = Alignment.Center,
                properties = PopupProperties(focusable = true),
                onDismissRequest = {},
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .shadow(1.dp, RoundedCornerShape(10.dp))
                        .background(colors.white, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    BasicText(text = "Использовать биометрию?", style = typography.h1ExtraBold)
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AppButton(
                            style = AppButtonStyle.Accent,
                            size = AppButtonSize.Chip,
                            content = "Да"
                        ) {
                            onEvent(CreatePinCodeEvent.BiometryUsedChanged(true))
                        }
                        AppButton(
                            style = AppButtonStyle.Default,
                            size = AppButtonSize.Chip,
                            content = "Нет"
                        ) {
                            onEvent(CreatePinCodeEvent.BiometryUsedChanged(false))
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewCreatePinCode() {
    val state = CreatePinCodeState(pincode = "4343")
    AppThemeProvider {
        ScreenProvider {
            CreatePinCodeContent(state) { }
        }
    }
}
