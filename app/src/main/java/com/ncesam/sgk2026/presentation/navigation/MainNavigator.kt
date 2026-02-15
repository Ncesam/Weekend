package com.ncesam.sgk2026.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.presentation.screens.SplashScreen
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
                    composable<AppRoute.Splash> {
                        SplashScreen()
                    }
                    authGraph(navController)
                    mainGraph()
                }
            }

            if (bottomTabs.isVisible) {
                Row(modifier = Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 22.dp, vertical = 8.dp).padding(bottom=30.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    AppBottomTabItem(
                        variant = AppBottomTabItemType.Home,
                        focused = currentDestination?.hierarchy?.any { destination -> destination.hasRoute<AppRoute.Main>() } == true) {
                        navigationManager.navigate(AppRoute.Main, clearToRoute = AppRoute.Main)
                    }
                    AppBottomTabItem(
                        variant = AppBottomTabItemType.Search,
                        focused = currentDestination?.hierarchy?.any { destination -> destination.hasRoute<AppRoute.Search>() } == true) {
                        navigationManager.navigate(AppRoute.Search(""), clearToRoute = AppRoute.Search(""))
                    }
                    AppBottomTabItem(
                        variant = AppBottomTabItemType.Travels,
                        focused = currentDestination?.hierarchy?.any { destination -> destination.hasRoute<AppRoute.Travels>() } == true) {
                        navigationManager.navigate(AppRoute.Travels, clearToRoute = AppRoute.Travels)
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

object AppNavigation {
    val navigator: NavigationManager
        @Composable get() = LocalNavigationManager.current
    val bottomTabs: BottomTabs
        @Composable get() = LocalBottomTabs.current
}