package com.example.personalfinance.data.record.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.personalfinance.common.TransactionType

@Entity
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    var transactionType : TransactionType = TransactionType.INCOME,

    val categoryId : Int,

    val accountId : Int,

    var notes : String = "",

    val date : String,

    val time : String,

    val amount : Double
)
