package com.example.personalfinance.data.record.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.personalfinance.common.TransactionType

@Entity
data class Record(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,

    var transactionType : TransactionType = TransactionType.INCOME,

    var categoryId : Int = -1,

    var accountId : Int = -1,

    var notes : String = "",

    var date : String = "",

    var time : String = "",

    var amount : Double = 0.0
){
    companion object{
        fun getTest()  = com.example.personalfinance.data.record.entity.Record()
    }
}