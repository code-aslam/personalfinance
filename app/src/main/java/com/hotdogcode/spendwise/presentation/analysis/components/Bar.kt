package com.hotdogcode.spendwise.presentation.analysis.components

import androidx.compose.ui.graphics.Color


data class Bar(
    var categoryId : Long = -1,
    var categoryName : String = "",
    var spending : Double = 0.0,
    var color : Color = Color.White,
    var percentage : Double = 0.0
)
