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
                   handleDrawer : () -> Unit,
                   categoryViewModel: CategoryViewModel,
                   accountViewModel: AccountViewModel) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val recordsViewModel : RecordsViewModel = hiltViewModel()
    NavHost(navController = navController,
        startDestination = BottomNavItem.Records.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None}){
        composable(BottomNavItem.Records.route) {
                Records(padding,
                handleDrawer,
                    recordsViewModel,
                navController)
        }
        composable(BottomNavItem.Budgets.route) { Budgets(padding, handleDrawer) }
        composable(BottomNavItem.Accounts.route) { Accounts(padding, handleDrawer, accountViewModel) }
        composable(BottomNavItem.Categories.route) { Categories(padding, handleDrawer, categoryViewModel) }
    }
}