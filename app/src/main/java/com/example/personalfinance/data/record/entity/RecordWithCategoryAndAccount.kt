package com.example.personalfinance.data.record.entity

import androidx.annotation.DrawableRes
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.common.TransactionType

data class RecordWithCategoryAndAccount(
    var recordId : Long = 0,

    var transactionType : TransactionType = TransactionType.INCOME,

    var categoryId : Long = -1,

    var accountId : Long = -1,

    var notes : String = "",

    var date : Long = 0,

    var time : String = "",

    var amount : Double = 0.0,

    val accountInitialAmount : Double = 0.0,
    val accountName : String = "",
    @DrawableRes
    val accountIcon : Int = -1,

    val categoryTitle :String = "",
    @DrawableRes
    var categoryIcon : Int = -1,
    var categoryType : CategoryType = CategoryType.INCOME
)
