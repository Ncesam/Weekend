package com.ncesam.sgk2026.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.MainEffect
import com.ncesam.sgk2026.domain.state.MainEvent
import com.ncesam.sgk2026.domain.state.MainState
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.MainViewModel
import com.ncesam.uikit.components.AppBottomSheet
import com.ncesam.uikit.components.AppButton
import com.ncesam.uikit.components.AppButtonGoToCart
import com.ncesam.uikit.components.AppButtonStyle
import com.ncesam.uikit.components.AppInputSearch
import com.ncesam.uikit.components.PrimaryAppCard
import com.ncesam.uikit.components.SmallAppCard
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val navigator = AppNavigation.navigator
    val bottomTabs = AppNavigation.bottomTabs
    bottomTabs.show()
    val showSnackBar = ScreenProvider.showSnackBar

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MainEffect.GoToBooking -> {
                    navigator.navigate(AppRoute.Booking(effect.hotel.id))
                }

                is MainEffect.ShowSnackBar -> {
                    showSnackBar(effect.msg)
                }

                MainEffect.GoToProfile -> {
                    navigator.navigate(AppRoute.Profile)
                }

                is MainEffect.GoToSearch -> {
                    navigator.navigate(AppRoute.Search(effect.value))
                }

                MainEffect.GoToCart -> {
                    navigator.navigate(AppRoute.ShopCart)
                }
            }
        }
    }

    MainContent(state) { event -> scope.launch { viewModel.onEvent(event) } }
}

@Composable
fun MainContent(state: MainState, onEvent: (MainEvent) -> Unit) {

    val colors = AppTheme.colors
    val typography = AppTheme.typography

    var searchFocused by remember { mutableStateOf(false) }

    val salesRowState = rememberLazyListState()
    val categoryRowState = rememberLazyListState()
    val hotelsColumnState = rememberLazyListState()

    val isDowned by remember { derivedStateOf { hotelsColumnState.firstVisibleItemIndex > 0 || hotelsColumnState.firstVisibleItemScrollOffset > 0 } }

    val categoryShape = RoundedCornerShape(10.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    AppInputSearch(
                        focused = searchFocused,
                        value = state.search,
                        placeholder = "Искать описание",
                        onChangeText = { text -> onEvent(MainEvent.SearchChanged(text)) },
                        onFocusChanged = { focusState -> searchFocused = focusState.isFocused })
                }
                Icon(
                    painter = AppTheme.icons.Profile,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = colors.icons
                )
            }

            AnimatedVisibility(
                visible = !isDowned,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    BasicText(
                        text = "Акции и новости",
                        style = typography.h3SemiBold,
                        color = { colors.caption })
                    LazyRow(state = salesRowState) {
                        items(state.allHotels.filter { hotel -> hotel.salesActive }) { hotel ->
                            val painter = rememberAsyncImagePainter(model = hotel.image)
                            val state by painter.state.collectAsState()

                            when (state) {
                                is AsyncImagePainter.State.Empty,
                                is AsyncImagePainter.State.Loading -> {
                                    CircularProgressIndicator()
                                }

                                is AsyncImagePainter.State.Success -> {
                                    SmallAppCard(
                                        image = painter,
                                        title = hotel.title
                                    ) {
                                        onEvent(MainEvent.SelectHotel(hotel.id))
                                    }
                                }

                                is AsyncImagePainter.State.Error -> {
                                    SmallAppCard(
                                        image = painter,
                                        title = hotel.title
                                    ) {
                                        onEvent(MainEvent.SelectHotel(hotel.id))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                BasicText(
                    text = "Каталог описаний",
                    style = typography.h3SemiBold,
                    color = { colors.caption })
                LazyRow(
                    state = categoryRowState,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.categories) { category ->
                        BasicText(
                            text = category,
                            modifier = Modifier
                                .background(
                                    if (category == state.selectedCategory) colors.accent else colors.inputBackground,
                                    categoryShape
                                )
                                .padding(vertical = 14.dp, horizontal = 20.dp)
                                .clickable { onEvent(MainEvent.SelectCategory(category)) },
                            style = typography.textMedium,
                            color = { if (category == state.selectedCategory) colors.white else colors.description })
                    }
                }
            }
            LazyColumn(
                state = hotelsColumnState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(state.filteredHotels) { hotel ->
                    val painter = rememberAsyncImagePainter(model = hotel.image)
                    val painterState by painter.state.collectAsState()

                    when (painterState) {
                        is AsyncImagePainter.State.Empty,
                        is AsyncImagePainter.State.Loading -> {
                            CircularProgressIndicator()
                        }

                        is AsyncImagePainter.State.Success -> {
                            PrimaryAppCard(
                                image = painter,
                                title = hotel.title,
                                price = hotel.cost,
                                added = hotel.id in state.addedHotels
                            ) {
                                onEvent(MainEvent.SelectHotel(hotel.id))
                            }
                        }

                        is AsyncImagePainter.State.Error -> {
                            PrimaryAppCard(
                                title = hotel.title,
                                price = hotel.cost,
                                added = hotel.id in state.addedHotels
                            ) {
                                onEvent(MainEvent.SelectHotel(hotel.id))
                            }
                        }
                    }
                }
            }

        }
        if (state.shopCartButtonActive) {
            AnimatedVisibility(
                visible = !isDowned,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .shadow(2.dp, ambientColor = colors.white, spotColor = colors.white)
                        .background(colors.white)
                        .padding(vertical = 32.dp, horizontal = 20.dp)
                ) {
                    AppButtonGoToCart(total = state.totalShopCart) {
                        onEvent(MainEvent.GoToCart)
                    }
                }
            }

        }
    }
    if (state.selectedHotel != null) {
        AppBottomSheet(
            { onEvent(MainEvent.SelectHotel("")) },
            expanded = true,
            name = state.selectedHotel.title
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(80.dp)) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BasicText(
                        text = "Описание", style = typography.captionSemiBold,
                        color = { colors.caption })
                    BasicText(
                        text = state.selectedHotel.description,
                        style = typography.textMedium
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(19.dp)) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        BasicText(
                            text = "Удобства",
                            style = typography.caption2Regular,
                            color = { colors.caption }
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            state.selectedHotel.facilities.forEach { value ->
                                BasicText(text = value, style = typography.textMedium)
                            }
                        }
                    }
                    AppButton(
                        style = AppButtonStyle.Accent,
                        content = "Добавить за ${state.selectedHotel.cost} ₽"
                    ) {
                        onEvent(MainEvent.AddToCart(state.selectedHotel.id))
                    }
                }
            }


        }
    }
}


@Preview
@Composable
fun PreviewMainScreen() {
    val state = MainState(addedHotels = listOf("323"))
    AppThemeProvider {
        ScreenProvider {
            MainContent(state) {}
        }
    }
}
