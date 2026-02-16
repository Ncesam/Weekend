package com.ncesam.sgk2026.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ncesam.sgk2026.domain.state.ProfileEvent
import com.ncesam.sgk2026.domain.state.ProfileState
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel()) {

}

@Composable
fun ProfileContent(state: ProfileState, onEvent: (ProfileEvent) -> Unit) {

}

@Preview
@Composable
fun PreviewProfileScreen() {
    val state = ProfileState()
    AppThemeProvider {
        ScreenProvider {
            ProfileContent(state) { }
        }
    }
}