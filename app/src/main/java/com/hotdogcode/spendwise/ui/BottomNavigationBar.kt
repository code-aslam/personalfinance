package com.hotdogcode.spendwise.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hotdogcode.spendwise.navigation.BottomNavItem
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.SoftPinkColor

@Composable
fun BottomNavigationBar(navController: NavController, padding : PaddingValues){
    BottomNavigation(
        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
        backgroundColor = MainColor
    ) {
        val context = LocalContext.current
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        var currentRoute = navBackStackEntry?.destination?.route

        BottomNavItem.all.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    when(item.route){
                        BottomNavItem.Budgets.route -> {
                            Toast.makeText(context, "AI Based Budget Coming Soon..", Toast.LENGTH_SHORT).show()
                        }
                        BottomNavItem.Analysis.route -> {
                            Toast.makeText(context, "AI Based Analysis Coming Soon..", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }

                },
                icon = {
                    if(item.route == BottomNavItem.Budgets.route || item.route == BottomNavItem.Analysis.route){
                        Image(
                            painter = painterResource(id = item.image), // Custom image for Home
                            contentDescription = item.label,
                            modifier = Modifier.size(if(currentRoute == item.route) 30.dp else 24.dp),
                            )
                    }else{
                    Image(
                    painter = painterResource(id = item.image), // Custom image for Home
                    contentDescription = item.label,
                    modifier = Modifier.size(if(currentRoute == item.route) 30.dp else 24.dp),
                    colorFilter = ColorFilter.tint(
                        if(currentRoute == item.route) SoftPinkColor else SecondaryColor
                    )
                )}},
                label = {
                    Text(item.label, fontSize = 8.sp, fontWeight = if(currentRoute == item.route) FontWeight.Bold else FontWeight.SemiBold, color = SecondaryColor)
                }
            )
        }


    }
}

