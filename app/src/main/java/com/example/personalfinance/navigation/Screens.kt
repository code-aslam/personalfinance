package com.example.personalfinance.navigation

import com.example.personalfinance.R

sealed class Screens(
    val route : String
) {
    data object HomeScreen : Screens("home_screen")
    data object CreateRecordScreen : Screens("create_records_screen")
}