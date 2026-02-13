package com.ncesam.sgk2026.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.uikit.components.AppBottomTabItem
import com.ncesam.uikit.components.AppBottomTabItemType


val LocalBottomTabs = staticCompositionLocalOf<BottomTabs> {
    error("Wrap your app MainNavigator")
}

val LocalNavigationManager = staticCompositionLocalOf<NavigationManager> {
    error("Wrap your app MainNavigator")
}

@Composable
fun MainNavigator() {
    val navController = rememberNavController()

    val backStateEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStateEntry?.destination


    val bottomTabs = remember {
        BottomTabs()
    }
    val navigationManager = remember {
        NavigationManager(navController)
    }
    CompositionLocalProvider(
        LocalBottomTabs provides bottomTabs,
        LocalNavigationManager provides navigationManager
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                NavHost(
                    navController = navController,
                    startDestination = AppRoute.Splash,
                ) {
                    authGraph(navController)
                }
            }

            if (bottomTabs.isVisible) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    AppBottomTabItem(
                        variant = AppBottomTabItemType.Home,
                        focused = currentDestination?.hierarchy?.any { destination -> destination.hasRoute<AppRoute.Profile>() } == true) {
                        navigationManager.navigate(AppRoute.Profile, clearToRoute = AppRoute.Profile)
                    }
                    AppBottomTabItem(
                        variant = AppBottomTabItemType.Search,
                        focused = currentDestination?.hierarchy?.any { destination -> destination.hasRoute<AppRoute.Profile>() } == true) {
                        navigationManager.navigate(AppRoute.Profile, clearToRoute = AppRoute.Profile)
                    }
                    AppBottomTabItem(
                        variant = AppBottomTabItemType.Travels,
                        focused = currentDestination?.hierarchy?.any { destination -> destination.hasRoute<AppRoute.Profile>() } == true) {
                        navigationManager.navigate(AppRoute.Profile, clearToRoute = AppRoute.Profile)
                    }
                    AppBottomTabItem(
                        variant = AppBottomTabItemType.Profile,
                        focused = currentDestination?.hierarchy?.any { destination -> destination.hasRoute<AppRoute.Profile>() } == true) {
                        navigationManager.navigate(AppRoute.Profile , clearToRoute = AppRoute.Profile)
                    }


                }
            }
        }

    }


}