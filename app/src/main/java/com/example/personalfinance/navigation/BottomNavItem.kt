package com.example.personalfinance.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route : String,
    val image : ImageVector,
    val label : String
) {
    data object Records : BottomNavItem("records", Icons.Default.Face, "Records")
    data object Budgets : BottomNavItem("budgets", Icons.Default.Face, "Budgets")
    data object Accounts : BottomNavItem("accounts", Icons.Default.Face, "Accounts")
    data object Categories : BottomNavItem("categories", Icons.Default.Face, "Categories")

    companion object{
        val all = listOf(Records, Budgets, Accounts, Categories)
    }
}