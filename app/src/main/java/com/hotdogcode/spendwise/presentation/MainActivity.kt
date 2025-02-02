package com.hotdogcode.spendwise.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.hotdogcode.spendwise.navigation.AppNavigation
import com.hotdogcode.spendwise.ui.theme.PersonalFinanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalFinanceTheme {
                SetupStatusBar()
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(){
    val mainNavController = rememberNavController()
    AppNavigation(mainNavController = mainNavController)
}


@Composable
fun SetupStatusBar(){
//    val statusBarLight = MainColor.toArgb()
//    val statusBarDark = MainColor.toArgb()
//    val navigationBarLight = Color.WHITE
//    val navigationBarDark = Color.BLACK
//    val isDarkMode = isSystemInDarkTheme()
//    val context = LocalContext.current as ComponentActivity
//
//    DisposableEffect(isDarkMode) {
//        context.enableEdgeToEdge(
//            statusBarStyle = when{!isDarkMode -> SystemBarStyle.light(statusBarLight, statusBarDark)
//                else -> SystemBarStyle.dark(statusBarDark)
//            },
//            navigationBarStyle = when{!isDarkMode -> SystemBarStyle.light(navigationBarLight, navigationBarDark)
//                else-> SystemBarStyle.dark(navigationBarDark)
//            }
//        )
//
//        onDispose { }
//    }
}














