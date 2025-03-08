package com.hotdogcode.spendwise.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hotdogcode.spendwise.presentation.accounts.components.Accounts
import com.hotdogcode.spendwise.presentation.analysis.components.Analysis
import com.hotdogcode.spendwise.presentation.budgets.components.Budgets
import com.hotdogcode.spendwise.presentation.categories.components.Categories
import com.hotdogcode.spendwise.presentation.records.components.Records

@Composable
fun BottomNavigationHostForMainScreen(navController: NavHostController,
                   padding : PaddingValues,
                   handleDrawer : () -> Unit) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value

    NavHost(navController = navController,
        startDestination = BottomNavItem.Records.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None}){
        composable(BottomNavItem.Records.route) {
            currentBackStackEntry?.let {
                Records(padding,
                    handleDrawer,
                    recordsViewModel = hiltViewModel(),
                    navController)
            }
        }
        composable(BottomNavItem.Budgets.route) {
            Budgets(padding, handleDrawer)
        }
        composable(BottomNavItem.Accounts.route) {
            currentBackStackEntry?.let {
                Accounts(padding, handleDrawer, accountViewModel = hiltViewModel(it))
            }
        }
        composable(BottomNavItem.Categories.route) {
            currentBackStackEntry?.let {
                Categories(padding, handleDrawer, viewModel = hiltViewModel(it))
            }
        }
        composable(BottomNavItem.Analysis.route) {
            currentBackStackEntry?.let {
                Analysis(padding, handleDrawer, viewModel = hiltViewModel(it))
            }
        }

    }
}