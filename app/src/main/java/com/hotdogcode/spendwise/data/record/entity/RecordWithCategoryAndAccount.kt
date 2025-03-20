package com.hotdogcode.spendwise.data.record.entity

import androidx.annotation.DrawableRes
import com.google.gson.Gson
import com.hotdogcode.spendwise.common.AccountType
import com.hotdogcode.spendwise.common.CategoryType
import com.hotdogcode.spendwise.common.IconName
import com.hotdogcode.spendwise.common.TransactionType

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
    val accountIcon : IconName = IconName.HOME,
    val accountType : AccountType = AccountType.BANK_ACCOUNT,
    //val accountBalance : Double = 0.0,

    val categoryTitle :String = "",
    var categoryIcon : IconName = IconName.HOME,
    var categoryType : CategoryType = CategoryType.INCOME
){
    fun toJson() = Gson().toJson(this)
}
