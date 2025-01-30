package com.example.personalfinance.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.accounts.components.Accounts
import com.example.personalfinance.presentation.budgets.components.Budgets
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.presentation.categories.components.Categories
import com.example.personalfinance.presentation.records.RecordsViewModel
import com.example.personalfinance.presentation.records.components.Records

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
    }
}