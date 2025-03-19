package com.hotdogcode.spendwise.data.accounts.entity

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.AccountType
import com.hotdogcode.spendwise.common.IconName

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true)
    var id : Long =  0,
    var initialAmount : Double = 0.0,
    var name : String,
    var balance : Double = 0.0,
    var icon : IconName = IconName.WALLET_BIG,
    var type : AccountType = AccountType.BANK_ACCOUNT
){
    companion object{
        fun testData() = Account(
            id = -1,
            initialAmount = 0.0,
            name = "Test"
        )
    }

}
