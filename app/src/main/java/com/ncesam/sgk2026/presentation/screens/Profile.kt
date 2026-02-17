package com.ncesam.sgk2026.presentation.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.MainEffect
import com.ncesam.sgk2026.domain.state.ProfileEffect
import com.ncesam.sgk2026.domain.state.ProfileEvent
import com.ncesam.sgk2026.domain.state.ProfileState
import com.ncesam.sgk2026.presentation.navigation.AppNavigation
import com.ncesam.sgk2026.presentation.viewModel.ProfileViewModel
import com.ncesam.uikit.components.AppButtonCircle
import com.ncesam.uikit.components.AppToggle
import com.ncesam.uikit.foundation.AppTheme
import com.ncesam.uikit.foundation.AppThemeProvider
import com.ncesam.uikit.foundation.ScreenProvider
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val navigator = AppNavigation.navigator
    val bottomTabs = AppNavigation.bottomTabs
    bottomTabs.show()
    val showSnackBar = ScreenProvider.showSnackBar

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
               ProfileEffect.GoToLogin -> {
                   navigator.navigate(AppRoute.AuthGraph, clearAll = true)
               }
                is ProfileEffect.ShowSnackBar -> {
                    showSnackBar(effect.msg)
                }
            }
        }
    }

    ProfileContent(state) { event -> scope.launch { viewModel.onEvent(event) } }
}

@Composable
fun ProfileContent(state: ProfileState, onEvent: (ProfileEvent) -> Unit) {

    val colors = AppTheme.colors
    val typography = AppTheme.typography

    val shape = RoundedCornerShape(15.dp)

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            onEvent(ProfileEvent.UploadAvatar(uri))
        }
    }

    Log.d("Test", state.firstName)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.white)
            .padding(horizontal = 20.dp)
            .padding(bottom = 44.dp)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            if (state.avatar != null) {
                AsyncImage(
                    model = state.avatar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
            } else {
                AppButtonCircle {
                    imageLauncher.launch("image/*")
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                BasicText(text = state.firstName, style = typography.h1ExtraBold)
                BasicText(text = state.email, style = typography.textMedium, color = { colors.caption })
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, shape = shape)
                    .border(1.dp, colors.inputStroke, shape)
                    .background(colors.white, shape)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp),
                horizontalAlignment = Alignment.Start
            ) {
                BasicText(text = "Информация о профиле", style = typography.h3SemiBold)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = AppTheme.icons.Point,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        BasicText(text = "Дата рождения", style = typography.h3SemiBold)
                        BasicText(text = state.dateInfo, style = typography.textRegular)
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = AppTheme.icons.Profile,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = colors.caption
                    )
                    Column {
                        BasicText(text = "Пол", style = typography.h3SemiBold)
                        BasicText(text = state.gender, style = typography.textRegular)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicText(text = "Уведомления", style = typography.h3SemiBold)
                    AppToggle(state.notificationActive) { onEvent(ProfileEvent.NotificationToggle) }
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BasicText(
                text = "Политика конфиденциальности",
                style = typography.textMedium,
                color = { colors.caption })
            BasicText(
                text = "Пользовательское соглашение",
                style = typography.textMedium,
                color = { colors.caption })
            BasicText(text = "Выход", style = typography.textMedium, color = { colors.error })
        }
    }

}

@Preview
@Composable
fun PreviewProfileScreen() {
    val state = ProfileState(firstName = "Павел")
    AppThemeProvider {
        ScreenProvider {
            ProfileContent(state) { }
        }
    }
}