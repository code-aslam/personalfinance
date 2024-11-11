package com.example.personalfinance.navigation

import androidx.annotation.DrawableRes
import com.example.personalfinance.R

sealed class BottomNavItem(
    val route : String,
    @DrawableRes val image : Int,
    val label : String
) {
    data object Records : BottomNavItem("records",R.drawable.record, "Records")
    data object Budgets : BottomNavItem("budgets", R.drawable.budget, "Budgets")
    data object Accounts : BottomNavItem("accounts",R.drawable.accounts, "Accounts")
    data object Categories : BottomNavItem("categories", R.drawable.category, "Categories")

    companion object{
        val all = listOf(Records, Budgets, Accounts, Categories)
    }
}