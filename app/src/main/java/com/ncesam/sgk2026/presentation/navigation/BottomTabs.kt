package com.ncesam.sgk2026.presentation.navigation

class BottomTabs(var isVisible: Boolean = false) {

    fun hide() {
        isVisible = false
    }

    fun show() {
        isVisible = true
    }


}