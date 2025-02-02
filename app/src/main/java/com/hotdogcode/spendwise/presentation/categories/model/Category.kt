package com.hotdogcode.spendwise.presentation.categories.model

import androidx.annotation.DrawableRes
import com.hotdogcode.spendwise.common.CategoryType

data class Category(
    val title : String,
    @DrawableRes
    val icon : Int,
    var type : CategoryType = CategoryType.INCOME
)
