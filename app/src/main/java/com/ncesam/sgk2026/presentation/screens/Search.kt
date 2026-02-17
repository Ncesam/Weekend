package com.ncesam.sgk2026.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
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
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.SearchEffect
import com.ncesam.sgk2026.domain.state.SearchEvent
import com.ncesam.sgk2026.domain.state.SearchState
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.SearchViewModel
import com.ncesam.uikit.components.AppBottomSheet
import com.ncesam.uikit.components.AppButton
import com.ncesam.uikit.components.AppButtonStyle
import com.ncesam.uikit.components.AppInputSearch
import com.ncesam.uikit.components.PrimaryAppCard
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SearchScreen(viewModel: SearchViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val navigator = AppNavigation.navigator
    val bottomTabs = AppNavigation.bottomTabs
    bottomTabs.show()
    val showSnackBar = ScreenProvider.showSnackBar

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.GoToBooking -> {
                    navigator.navigate(AppRoute.Booking(effect.hotel.id))
                }

                is SearchEffect.ShowSnackBar -> {
                    showSnackBar(effect.msg)
                }
            }
        }
    }

    SearchContent(state) { event -> scope.launch { viewModel.onEvent(event) } }


}

@Composable
fun SearchContent(state: SearchState, onEvent: (SearchEvent) -> Unit) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val filteredHotelState = rememberLazyListState()
    var inputFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(text = "Поиск", style = typography.h2SemiBold)
        AppInputSearch(
            focused = inputFocused,
            value = state.searchValue,
            placeholder = "Поиск",
            onChangeText = { text -> onEvent(SearchEvent.SearchValueChanged(text)) },
            onSearch = {
                inputFocused = false
                onEvent(SearchEvent.Search)
            }) { focusState ->
            inputFocused = focusState.isFocused
        }
        LazyColumn(
            state = filteredHotelState,
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
                        ) {
                            onEvent(SearchEvent.SelectHotel(hotel.id))
                        }
                    }

                    is AsyncImagePainter.State.Error -> {
                        PrimaryAppCard(
                            title = hotel.title,
                            price = hotel.cost,
                        ) {
                            onEvent(SearchEvent.SelectHotel(hotel.id))
                        }
                    }
                }
            }
        }
        if (state.selectedHotel != null) {
            AppBottomSheet(
                { onEvent(SearchEvent.SelectHotel("")) },
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
                            onEvent(SearchEvent.AddToCart(state.selectedHotel.id))
                        }
                    }
                }

            }
        }
    }

}

@Preview
@Composable
fun PreviewSearchScreen() {
    val state = SearchState()
    AppThemeProvider {
        ScreenProvider {
            SearchContent(state) { }
        }
    }
}