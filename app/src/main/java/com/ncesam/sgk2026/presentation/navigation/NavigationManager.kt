package com.ncesam.sgk2026.presentation.navigation

import androidx.navigation.NavController
import com.ncesam.sgk2026.domain.navigation.AppRoute

class NavigationManager(private val navController: NavController) {
    fun navigate(route: AppRoute, clearToRoute: AppRoute? = null, clearAll: Boolean = false) {
        navController.navigate(route = route) {
            if (clearAll) {
                popUpTo(0)
            }
            if (clearToRoute != null) {
                popUpTo(clearToRoute) {
                    inclusive = true
                }
            }
        }
    }

    fun back() {
        navController.popBackStack()
    }
}