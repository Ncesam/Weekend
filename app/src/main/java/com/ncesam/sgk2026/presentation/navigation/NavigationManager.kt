package com.ncesam.sgk2026.presentation.navigation

import androidx.navigation.NavController
import com.ncesam.sgk2026.domain.navigation.AppRoute

class NavigationManager(private val navController: NavController) {
    fun navigate(route: AppRoute, clearToRoute: AppRoute? = null) {
        navController.navigate(route) {
            if (clearToRoute != null) {
                popUpTo(clearToRoute)
            }
        }
    }

    fun back() {
        navController.popBackStack()
    }
}