package com.ncesam.sgk2026.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.MainEffect
import com.ncesam.sgk2026.domain.state.TravelsEffect
import com.ncesam.sgk2026.domain.state.TravelsEvent
import com.ncesam.sgk2026.domain.state.TravelsState
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.TravelsViewModel
import com.ncesam.uikit.components.PrimaryAppCard
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun TravelsScreen(viewModel: TravelsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val bottomTabs = AppNavigation.bottomTabs
    bottomTabs.show()
    val showSnackBar = ScreenProvider.showSnackBar

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TravelsEffect.ShowSnackBar -> {
                    showSnackBar(effect.msg)
                }
            }
        }
    }

    TravelsContent(state) { event -> scope.launch { viewModel.onEvent(event) } }
}

@Composable
fun TravelsContent(state: TravelsState, onEvent: (TravelsEvent) -> Unit) {
    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val travelsColumnState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .statusBarsPadding()
            .padding(top = 30.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicText(
                text = "Поездки", style = typography.h2SemiBold, modifier = Modifier.align(
                    Alignment.Center
                )
            )
            Icon(
                painter = AppTheme.icons.Plus,
                tint = colors.icons,
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.CenterEnd
                    )
                    .size(20.dp)
                    .clickable { onEvent(TravelsEvent.AddClicked) }
            )
        }
        LazyColumn(state = travelsColumnState, verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(bottom = 20.dp)) {
            items(state.items) { hotel ->
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
                            added = true
                        ) {
                            onEvent(TravelsEvent.DeleteTravel(hotel.id))
                        }
                    }

                    is AsyncImagePainter.State.Error -> {
                        PrimaryAppCard(
                            title = hotel.title,
                            price = hotel.cost,
                            added = true
                        ) {
                            onEvent(TravelsEvent.DeleteTravel(hotel.id))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewTravelsScreen() {
    val state = TravelsState()
    AppThemeProvider {
        ScreenProvider {
            TravelsContent(state) { }
        }
    }
}