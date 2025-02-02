package com.hotdogcode.spendwise.navigation

sealed class Screens(
    val route : String
) {
    data object HomeScreen : Screens("home_screen")
    data object CreateRecordScreen : Screens("create_records_screen")
}