package com.ncesam.uikit.foundation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ncesam.uikit.components.AppSnackBar
import kotlinx.coroutines.launch


val LocalShowSnackBar = staticCompositionLocalOf<(String) -> Unit> {
    error("Wrap Screen Provider")
}

@Composable
fun ScreenProvider(content: @Composable () -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val showSnackBar: (String) -> Unit = { value ->
        scope.launch { snackBarHostState.showSnackbar(value) }
    }
    CompositionLocalProvider(
        LocalShowSnackBar provides showSnackBar
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.align(Alignment.TopCenter)
            ) { data ->
                AppSnackBar(data.visuals.message) { data.dismiss() }
            }
        }
    }
}

object ScreenProvider {
    val showSnackBar: (String) -> Unit
        @Composable get() = LocalShowSnackBar.current
}

