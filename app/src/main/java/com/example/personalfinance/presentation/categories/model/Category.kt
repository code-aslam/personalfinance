package com.example.personalfinance.presentation.categories.model

import androidx.annotation.DrawableRes
import com.example.personalfinance.common.CategoryType

data class Category(
    val title : String,
    @DrawableRes
    val icon : Int,
    var type : CategoryType = CategoryType.INCOME
)
