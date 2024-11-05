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
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.ui.BottomNavigationBar
import com.example.personalfinance.ui.theme.Beige
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(categoryViewModel: CategoryViewModel,
               accountViewModel: AccountViewModel,
               mainNavController: NavHostController
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
                            containerColor = Beige,
                            shape = CircleShape
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "")
                        }
                    },
                    bottomBar = {
                        BottomNavigationBar(navController = navController, innerPadding)
                    },
                ) { innerPadding ->
                    HomeScreenConfiguration(
                        navController,
                        innerPadding,
                        categoryViewModel,
                        accountViewModel
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
fun HomeScreenConfiguration(navController: NavHostController, padding : PaddingValues, categoryViewModel: CategoryViewModel, accountViewModel: AccountViewModel, handleDrawer : () -> Unit){
    BottomNavigationHostForMainScreen(navController, padding, handleDrawer, categoryViewModel,accountViewModel)
}
