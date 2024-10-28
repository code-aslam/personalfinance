package com.example.personalfinance.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.personalfinance.navigation.NavigationHost
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.ui.BottomNavigationBar
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.PersonalFinanceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel : HomeViewModel by viewModels()
    private val categoryViewModel : CategoryViewModel by viewModels()
    private val accountViewModel : AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalFinanceTheme {
                setupStatusBar()
                MainRenderer(categoryViewModel,accountViewModel)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainRenderer(categoryViewModel: CategoryViewModel, accountViewModel: AccountViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold ()
    {
        innerPadding->
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Box(modifier = Modifier.padding(innerPadding)){
                    ModalDrawerSheet(
                        drawerShape = RectangleShape
                    ) { /* Drawer content */ }
                }

            },
            content = {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController, innerPadding)
                    },
                ) {
                        innerPadding->
                    MainConfiguration(navController = navController,innerPadding, categoryViewModel, accountViewModel){
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainConfiguration(navController: NavHostController, padding : PaddingValues, categoryViewModel: CategoryViewModel, accountViewModel: AccountViewModel, handleDrawer : () -> Unit){
    NavigationHost(navController = navController, padding, handleDrawer, categoryViewModel,accountViewModel)
}

@Composable
fun setupStatusBar(){
    val statusBarLight = Beige.toArgb()
    val statusBarDark = Beige.toArgb()
    val navigationBarLight = Color.WHITE
    val navigationBarDark = Color.BLACK
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current as ComponentActivity

    DisposableEffect(isDarkMode) {
        context.enableEdgeToEdge(
            statusBarStyle = if (!isDarkMode) {
                SystemBarStyle.light(
                    statusBarLight,
                    statusBarDark
                )
            } else {
                SystemBarStyle.dark(
                    statusBarDark
                )
            },
            navigationBarStyle = if(!isDarkMode){
                SystemBarStyle.light(
                    navigationBarLight,
                    navigationBarDark
                )
            } else {
                SystemBarStyle.dark(navigationBarDark)
            }
        )

        onDispose { }
    }
}

