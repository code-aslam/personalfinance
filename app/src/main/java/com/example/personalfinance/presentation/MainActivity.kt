package com.example.personalfinance.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.personalfinance.navigation.NavigationHost
import com.example.personalfinance.presentation.records.HomeViewModel
import com.example.personalfinance.ui.BottomNavigationBar
import com.example.personalfinance.ui.theme.PersonalFinanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalFinanceTheme {
//                val recordList by homeViewModel.recordList.collectAsState()
//                homeViewModel.test()
                MainRenderer()
            }
        }
    }
}

@Composable
fun MainRenderer(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ){innerPadding->
        MainConfiguration(navController = navController, innerPadding)
    }
}

@Composable
fun MainConfiguration(navController: NavHostController, padding : PaddingValues){
    NavigationHost(navController = navController, padding)
}