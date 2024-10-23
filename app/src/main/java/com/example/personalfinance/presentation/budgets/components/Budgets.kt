package com.example.personalfinance.presentation.budgets.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Budgets(padding: PaddingValues){
    Column(modifier = Modifier.padding(padding)) {
        Text(text = "Budgets")
    }
}