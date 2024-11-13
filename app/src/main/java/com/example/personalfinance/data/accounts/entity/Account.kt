package com.example.personalfinance.data.accounts.entity

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.personalfinance.R

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true)
    var id : Long =  0,
    var initialAmount : Int = 0,
    var name : String,

    @DrawableRes
    var icon : Int
){
    companion object{
        fun testData() = Account(
            id = -1,
            initialAmount = 0,
            name = "Test",
            icon = R.drawable.walletbig
        )
    }

}
