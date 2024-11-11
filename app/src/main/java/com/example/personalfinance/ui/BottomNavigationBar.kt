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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.personalfinance.R
import com.example.personalfinance.navigation.BottomNavItem
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.PBGFont
import com.example.personalfinance.ui.theme.SecondaryColor
import com.example.personalfinance.ui.theme.SoftPinkColor

@Composable
fun BottomNavigationBar(navController: NavController, padding : PaddingValues){
    BottomNavigation(
        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
        backgroundColor = MainColor
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        var currentRoute = navBackStackEntry?.destination?.route

        BottomNavItem.all.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Image(
                    painter = painterResource(id = item.image), // Custom image for Home
                    contentDescription = item.label,
                    modifier = Modifier.size(if(currentRoute == item.route) 30.dp else 24.dp),
                        colorFilter = ColorFilter.tint(if(currentRoute == item.route) SoftPinkColor else SecondaryColor)
                )},
                label = {
                    Text(item.label, fontSize = 10.sp, fontWeight = if(currentRoute == item.route) FontWeight.Bold else FontWeight.Normal, color = SecondaryColor)
                }
            )
        }


    }
}

