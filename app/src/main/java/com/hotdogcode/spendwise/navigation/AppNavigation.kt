package com.hotdogcode.spendwise.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hotdogcode.spendwise.presentation.createrecord.components.CreateRecordScreen
import com.hotdogcode.spendwise.presentation.home.components.HomeScreen


@Composable
fun AppNavigation(
    mainNavController : NavHostController,
    startDestination : String = Screens.HomeScreen.route
){
    val currentBackStackEntry = mainNavController.currentBackStackEntryAsState().value

    NavHost(navController = mainNavController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None}){
        composable(Screens.HomeScreen.route) {
            HomeScreen(mainNavController = mainNavController)
        }
        composable(Screens.CreateRecordScreen.route) {
            currentBackStackEntry?.let {
                CreateRecordScreen(mainNavController,
                    accountViewModel = hiltViewModel(it),
                    recordsViewModel = hiltViewModel(it),
                    categoryViewModel = hiltViewModel(it),
                    createRecordViewModel = hiltViewModel(it))
            }

        }
    }
}