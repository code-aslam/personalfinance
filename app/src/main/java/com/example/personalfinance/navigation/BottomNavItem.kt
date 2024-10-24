package com.example.personalfinance.navigation

import androidx.annotation.DrawableRes
import com.example.personalfinance.R

sealed class BottomNavItem(
    val route : String,
    @DrawableRes val image : Int,
    val label : String
) {
    data object Records : BottomNavItem("records",R.drawable.ledger, "Records")
    data object Budgets : BottomNavItem("budgets", R.drawable.budget, "Budgets")
    data object Accounts : BottomNavItem("accounts",R.drawable.accounting, "Accounts")
    data object Categories : BottomNavItem("categories", R.drawable.choice, "Categories")

    companion object{
        val all = listOf(Records, Budgets, Accounts, Categories)
    }
}