package com.example.personalfinance.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.personalfinance.presentation.MainScreen
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.presentation.createrecord.components.CreateRecordScreen
import com.example.personalfinance.presentation.home.components.HomeScreen
import com.example.personalfinance.presentation.records.RecordsViewModel


@Composable
fun AppNavigation(
    accountViewModel: AccountViewModel,
    categoryViewModel: CategoryViewModel,
    mainNavController : NavHostController
){
    val currentBackStackEntry by mainNavController.currentBackStackEntryAsState()
    NavHost(navController = mainNavController,
        startDestination = Screens.HomeScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None}){
        composable(Screens.HomeScreen.route) { HomeScreen(accountViewModel = accountViewModel, categoryViewModel = categoryViewModel, mainNavController = mainNavController) }
        composable(Screens.CreateRecordScreen.route) { CreateRecordScreen(mainNavController) }
    }
}