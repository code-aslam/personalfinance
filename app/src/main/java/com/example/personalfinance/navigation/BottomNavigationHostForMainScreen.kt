package com.example.personalfinance.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.accounts.components.Accounts
import com.example.personalfinance.presentation.budgets.components.Budgets
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.presentation.categories.components.Categories
import com.example.personalfinance.presentation.records.components.Records

@Composable
fun BottomNavigationHostForMainScreen(navController: NavHostController,
                   padding : PaddingValues,
                   handleDrawer : () -> Unit,
                   categoryViewModel: CategoryViewModel,
                   accountViewModel: AccountViewModel) {
    NavHost(navController = navController,
        startDestination = BottomNavItem.Records.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None}){
        composable(BottomNavItem.Records.route) { Records(padding, handleDrawer) }
        composable(BottomNavItem.Budgets.route) { Budgets(padding, handleDrawer) }
        composable(BottomNavItem.Accounts.route) { Accounts(padding, handleDrawer, accountViewModel) }
        composable(BottomNavItem.Categories.route) { Categories(padding, handleDrawer, categoryViewModel) }
    }
}