package com.example.personalfinance.presentation.categories.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Categories(padding :PaddingValues){
    Column(modifier = Modifier.padding(padding)) {
        Text(text = "Categories")
    }
}