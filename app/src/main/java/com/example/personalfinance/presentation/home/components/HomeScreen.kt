package com.example.personalfinance.presentation.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.personalfinance.navigation.BottomNavigationHostForMainScreen
import com.example.personalfinance.navigation.Screens
import com.example.personalfinance.ui.BottomNavigationBar
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.SecondaryColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(mainNavController: NavHostController,
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold()
    { innerPadding ->
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Box(modifier = Modifier.padding(innerPadding)) {
                    ModalDrawerSheet(
                        drawerShape = RectangleShape
                    ) { /* Drawer content */ }
                }

            },
            content = {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                mainNavController.navigate(Screens.CreateRecordScreen.route){
                                    popUpTo(mainNavController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            containerColor = MainColor,
                            shape = CircleShape
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "", tint = SecondaryColor)
                        }
                    },
                    bottomBar = {
                        BottomNavigationBar(navController = navController, innerPadding)
                    },
                ) { innerPadding ->
                    HomeScreenConfiguration(
                        navController,
                        innerPadding
                    ) {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                }
            }
        )
    }

}




@Composable
fun HomeScreenConfiguration(navController: NavHostController, padding : PaddingValues,handleDrawer : () -> Unit){
    BottomNavigationHostForMainScreen(navController, padding, handleDrawer)
}
