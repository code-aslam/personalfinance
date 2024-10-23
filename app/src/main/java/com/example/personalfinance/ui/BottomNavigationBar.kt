package com.example.personalfinance.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.personalfinance.navigation.BottomNavItem

@Composable
fun BottomNavigationBar(navController: NavController){
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavItem.all.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier.padding(2.dp),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.image, contentDescription = "")},
                label = {Text(item.label, fontSize = 10.sp)}
            )
        }
    }
}