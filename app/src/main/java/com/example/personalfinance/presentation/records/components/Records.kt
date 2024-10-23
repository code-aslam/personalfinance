package com.example.personalfinance.presentation.records.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Records(padding : PaddingValues){
    Column(modifier = Modifier.padding(padding)) {
        Text(text = "Home")
    }

}