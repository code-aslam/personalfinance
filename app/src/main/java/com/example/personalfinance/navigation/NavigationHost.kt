package com.example.personalfinance.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.personalfinance.presentation.accounts.components.Accounts
import com.example.personalfinance.presentation.budgets.components.Budgets
import com.example.personalfinance.presentation.categories.components.Categories
import com.example.personalfinance.presentation.records.components.Records

@Composable
fun NavigationHost(navController: NavHostController, padding : PaddingValues, handleDrawer : () -> Unit) {
    NavHost(navController = navController, startDestination = BottomNavItem.Records.route){
        composable(BottomNavItem.Records.route) { Records(padding, handleDrawer) }
        composable(BottomNavItem.Budgets.route) { Budgets(padding, handleDrawer) }
        composable(BottomNavItem.Accounts.route) { Accounts(padding) }
        composable(BottomNavItem.Categories.route) { Categories(padding) }
    }
}