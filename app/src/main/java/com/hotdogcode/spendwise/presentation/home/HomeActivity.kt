package com.hotdogcode.spendwise.presentation.home

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.hotdogcode.spendwise.navigation.AppNavigation
import com.hotdogcode.spendwise.presentation.home.ui.theme.PersonalFinanceTheme
import com.hotdogcode.spendwise.ui.theme.MainColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(MainColor.toArgb(),Color.BLACK)
        )
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