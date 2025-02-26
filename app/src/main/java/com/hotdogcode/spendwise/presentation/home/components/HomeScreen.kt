package com.hotdogcode.spendwise.presentation.home.components

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.navigation.AppNavigation
import com.hotdogcode.spendwise.navigation.BottomNavigationHostForMainScreen
import com.hotdogcode.spendwise.navigation.Screens
import com.hotdogcode.spendwise.presentation.home.HomeViewModel
import com.hotdogcode.spendwise.presentation.smartpurchase.SmartPurchaseActivity
import com.hotdogcode.spendwise.ui.BottomNavigationBar
import com.hotdogcode.spendwise.ui.DrawerItem
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.PBGFont
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.SoftPinkColor
import com.hotdogcode.spendwise.ui.theme.brightGreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(mainNavController: NavHostController,
) {
    val context = LocalContext.current


    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val homeViewModel : HomeViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        homeViewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold()
    { innerPadding ->

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
                    .background(MainColor)
                    .padding(innerPadding)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo_removebg),
                            contentDescription = "",
                            modifier = Modifier.size(50.dp)
                        )
                        Text(
                            text = "SpendWise",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PBGFont,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Text(
                        text = "Your AI-powered saving companion.",
                        style = TextStyle(
                            fontSize = 20.sp,
                            brush = Brush.linearGradient(
                                colors = listOf(DarkForestGreenColor, Color.Green, SoftPinkColor,
                                    DarkForestGreenColor)
                            )
                        ),
                        fontWeight = FontWeight.Normal,
                        fontFamily = PBGFont,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        textAlign = TextAlign.Center

                    )

                    Divider()
                    DrawerItem("Smart Purchase Advisor", R.drawable.smart_purchase_suggestion_icon){
                        context.startActivity(Intent(context,SmartPurchaseActivity::class.java))
                    }
//                    DrawerItem("Clear Data", Icons.Default.Delete){
//                        homeViewModel.clearAllTables()
//                    }
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
                            Icon(Icons.Default.Add, contentDescription = "", tint = brightGreen)
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
