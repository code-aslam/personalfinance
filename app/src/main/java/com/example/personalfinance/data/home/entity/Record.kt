package com.example.personalfinance.data.home.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val amount : Double
)
