package com.example.personalfinance.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.personalfinance.R
import com.example.personalfinance.navigation.BottomNavItem
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.PBGFont

@Composable
fun BottomNavigationBar(navController: NavController, padding : PaddingValues){
    BottomNavigation(
        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
        backgroundColor = Beige
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavItem.all.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {  Image(
                    painter = painterResource(id = item.image), // Custom image for Home
                    contentDescription = item.label,
                    modifier = Modifier.size(24.dp)
                )},
                label = {Text(item.label, fontSize = 10.sp, fontFamily = PBGFont, fontWeight = FontWeight.Normal)}
            )
        }
    }
}