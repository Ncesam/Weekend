package com.ncesam.sgk2026.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class BottomTabs(isVisible: Boolean = false) {
    var isVisible by mutableStateOf(isVisible)
    fun hide() {
        isVisible = false
    }

    fun show() {
        isVisible = true
    }


}