package com.example.personalfinance.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.personalfinance.navigation.AppNavigation
import com.example.personalfinance.navigation.BottomNavigationHostForMainScreen
import com.example.personalfinance.navigation.Screens
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.presentation.records.RecordsViewModel
import com.example.personalfinance.ui.BottomNavigationBar
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.PersonalFinanceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val recordsViewModel : RecordsViewModel by viewModels()
    private val categoryViewModel : CategoryViewModel by viewModels()
    private val accountViewModel : AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalFinanceTheme {
                SetupStatusBar()
                MainScreen(recordsViewModel = recordsViewModel,
                    categoryViewModel = categoryViewModel,
                    accountViewModel = accountViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(accountViewModel: AccountViewModel,
               categoryViewModel: CategoryViewModel,
               recordsViewModel: RecordsViewModel){
    val mainNavController = rememberNavController()
    AppNavigation(accountViewModel = accountViewModel,
        categoryViewModel = categoryViewModel,
        recordsViewModel = recordsViewModel,
        mainNavController = mainNavController)
}


@Composable
fun SetupStatusBar(){
    val statusBarLight = Beige.toArgb()
    val statusBarDark = Beige.toArgb()
    val navigationBarLight = Color.WHITE
    val navigationBarDark = Color.BLACK
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current as ComponentActivity

    DisposableEffect(isDarkMode) {
        context.enableEdgeToEdge(
            statusBarStyle = when{!isDarkMode -> SystemBarStyle.light(statusBarLight, statusBarDark)
                else -> SystemBarStyle.dark(statusBarDark)
            },
            navigationBarStyle = when{!isDarkMode -> SystemBarStyle.light(navigationBarLight, navigationBarDark)
                else-> SystemBarStyle.dark(navigationBarDark)
            }
        )

        onDispose { }
    }



}














