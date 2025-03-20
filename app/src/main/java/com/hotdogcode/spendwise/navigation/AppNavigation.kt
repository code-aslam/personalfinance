package com.hotdogcode.spendwise.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.hotdogcode.spendwise.presentation.createrecord.components.CreateRecordScreen
import com.hotdogcode.spendwise.presentation.home.components.HomeScreen
import com.hotdogcode.spendwise.presentation.smartpurchase.components.SmartPurchaseScreen


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun AppNavigation(
    mainNavController : NavHostController,
    startDestination : String = Screens.HomeScreen.route
){
    val currentBackStackEntry = mainNavController.currentBackStackEntryAsState().value
    val slideInTransition = slideInHorizontally { it * 2 }
    val slideOutTransition = slideOutHorizontally { -it * 2 }
    NavHost(navController = mainNavController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None}){
        composable(Screens.HomeScreen.route) {
            HomeScreen(mainNavController = mainNavController)
        }
//        composable(Screens.CreateRecordScreen.route) {
//            AnimatedContent(
//                targetState = Screens.CreateRecordScreen.route,
//                transitionSpec = {
//                    scaleIn(animationSpec = tween(durationMillis = 500)) togetherWith
//                            scaleOut(animationSpec = tween(durationMillis = 500))
//                }
//            ) {
//                currentBackStackEntry?.let {
//                        entry ->
//                    CreateRecordScreen(mainNavController,
//                        accountViewModel = hiltViewModel(entry),
//                        recordsViewModel = hiltViewModel(entry),
//                        categoryViewModel = hiltViewModel(entry),
//                        createRecordViewModel = hiltViewModel(entry))
//                }
//
//            }
//
//        }


        composable(route = "${Screens.CreateRecordScreen.route}/{selectedRecord}",
            arguments = listOf(navArgument("selectedRecord") {
                type = NavType.StringType
            }))
        { backStackEntry ->
            val selectedRecord = backStackEntry.arguments?.getString("selectedRecord")
            AnimatedContent(
                targetState = Screens.CreateRecordScreen.route,
                transitionSpec = {
                    scaleIn(animationSpec = tween(durationMillis = 500)) togetherWith
                            scaleOut(animationSpec = tween(durationMillis = 500))
                }
            ) {
                currentBackStackEntry?.let {
                        entry ->
                    CreateRecordScreen(mainNavController,
                        accountViewModel = hiltViewModel(entry),
                        recordsViewModel = hiltViewModel(entry),
                        categoryViewModel = hiltViewModel(entry),
                        createRecordViewModel = hiltViewModel(entry),
                        selectedRecord = selectedRecord)
                }

            }
        }


    }
}