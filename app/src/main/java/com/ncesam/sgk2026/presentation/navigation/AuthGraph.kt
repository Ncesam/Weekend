package com.ncesam.sgk2026.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ncesam.sgk2026.domain.navigation.AppRoute

fun NavGraphBuilder.authGraph(navController: NavController) {

    navigation< AppRoute.AuthGraph>(
        startDestination = AppRoute.Login
    ) {
        composable<AppRoute.Login> {

        }
        composable<AppRoute.CreateProfile> {

        }
        composable<AppRoute.CreatePassword> {

        }
        composable<AppRoute.CreatePinCode> {

        }
    }
}