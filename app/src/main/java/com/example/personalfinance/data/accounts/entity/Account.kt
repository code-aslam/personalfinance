package com.example.personalfinance.data.accounts.entity

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true)
    var id : Long =  0,
    var initialAmount : Int = 0,
    var name : String,

    @DrawableRes
    var icon : Int
)
