package com.hotdogcode.spendwise.presentation.smartpurchase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hotdogcode.spendwise.presentation.smartpurchase.components.SmartPurchase
import com.hotdogcode.spendwise.presentation.smartpurchase.ui.theme.PersonalFinanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmartPurchaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalFinanceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SmartPurchase(innerPadding)
                }
            }
        }
    }
}
