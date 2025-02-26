package com.hotdogcode.spendwise.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.hotdogcode.spendwise.navigation.AppNavigation
import com.hotdogcode.spendwise.presentation.home.ui.theme.PersonalFinanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.WHITE, Color.BLACK)
        )
        setContent {
            PersonalFinanceTheme {
                AppNavigation(
                    mainNavController = rememberNavController())
            }
        }
    }

    /**
    fun testTensorFlowSetup(){
        val tfliteModel: Interpreter

        try {
            val fileDescriptor = assets.openFd("spendwise_purchase_advisor.tflite")
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            val buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

            tfliteModel = Interpreter(buffer)
            val suggestion = predictPurchase(
                tfliteModel = tfliteModel,
                accountBalance = 2500.0f,
                creditLimit = 500.0f,
                monthlyIncome = 1000.0f,
                monthlyExpense = 1000.0f,
                debtToIncomeRatio = 0.5f,
                category = 1,
                necessityScore = 0.7f,
                relativeAmount = 0.5f,
                dayOfWeek = 1.0f,
                hourOfDay = 12.0f,
                amount = 500.0f
            )
            Toast.makeText(this, suggestion, Toast.LENGTH_SHORT).show()
            Log.d("TFLite", "Model loaded successfully!")
        } catch (e: Exception) {
            Log.e("TFLite", "Error loading model: ${e.message}")
        }




    }
    fun predictPurchase(
        tfliteModel : Interpreter,
        accountBalance: Float,
        creditLimit: Float,
        monthlyIncome: Float,
        monthlyExpense: Float,
        debtToIncomeRatio: Float,
        category: Int,
        necessityScore: Float,
        relativeAmount: Float,
        dayOfWeek: Float,
        hourOfDay: Float,
        amount: Float
    ): String {
        val input = arrayOf(
            floatArrayOf(
                accountBalance, creditLimit, monthlyIncome, monthlyExpense,
                debtToIncomeRatio, category.toFloat(), necessityScore,
                relativeAmount, dayOfWeek, hourOfDay, amount
            )
        )

        val output = arrayOf(FloatArray(1))  // Output buffer

        tfliteModel.run(input, output)  // Run inference

        val prediction = output[0][0]
        return if (prediction > 0.5) "Good Purchase ✅" else "Bad Purchase ❌"
    }
    **/
}

@Composable
fun MainScreen(innerPaddingValues: PaddingValues){
//    val mainNavController = rememberNavController()
//    AppNavigation(mainNavController = mainNavController, innerPaddingValues = innerPaddingValues)
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