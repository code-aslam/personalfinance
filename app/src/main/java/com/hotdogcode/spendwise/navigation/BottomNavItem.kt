package com.hotdogcode.spendwise.navigation

import androidx.annotation.DrawableRes
import com.hotdogcode.spendwise.R

sealed class BottomNavItem(
    val route : String,
    @DrawableRes val image : Int,
    val label : String
) {
    data object Records : BottomNavItem("records",R.drawable.record, "Records")
    data object Analysis : BottomNavItem("analysis", R.drawable.budget_analysis_soon, "Analysis")
    data object Budgets : BottomNavItem("budgets", R.drawable.budget_soon, "Budgets")
    data object Accounts : BottomNavItem("accounts",R.drawable.accounts, "Accounts")
    data object Categories : BottomNavItem("categories", R.drawable.category, "Categories")

    companion object{
        val all = listOf(Records, Budgets, Analysis, Accounts, Categories)
    }
}