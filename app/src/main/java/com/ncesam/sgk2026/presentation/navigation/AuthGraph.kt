package com.ncesam.sgk2026.presentation.navigation

import CreatePinCodeScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.presentation.screens.CreatePasswordScreen
import com.ncesam.sgk2026.presentation.screens.CreateProfileScreen
import com.ncesam.sgk2026.presentation.screens.LoginScreen
import com.ncesam.sgk2026.presentation.viewModel.RegistrationViewModel
import org.koin.compose.viewmodel.sharedKoinViewModel

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<AppRoute.AuthGraph>(
        startDestination = AppRoute.Login
    ) {
        composable<AppRoute.Login> {
            LoginScreen()
        }
        composable<AppRoute.CreateProfile> { backStackEntry ->
            val sharedVM = backStackEntry.sharedKoinViewModel<RegistrationViewModel>(
                navController,
                AppRoute.AuthGraph
            )
            CreateProfileScreen(sharedVM)
        }
        composable<AppRoute.CreatePassword> { backStackEntry ->
            val sharedVM = backStackEntry.sharedKoinViewModel<RegistrationViewModel>(
                navController,
                AppRoute.AuthGraph
            )
            CreatePasswordScreen(sharedVM)
        }
        composable<AppRoute.CreatePinCode> {
            CreatePinCodeScreen()
        }
    }
}