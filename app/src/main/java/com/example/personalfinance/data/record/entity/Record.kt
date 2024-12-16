package com.example.personalfinance.data.record.entity

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.common.TransactionType
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.category.entity.Category

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId"), Index("accountId")]
)
data class Record(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,

    var transactionType : TransactionType = TransactionType.INCOME,

    var categoryId : Long = -1,

    var accountId : Long = -1,

    var notes : String = "",

    var date : Long = 0,

    var time : String = "",

    var amount : Double = 0.0

){
    companion object{
        fun getTest()  = com.example.personalfinance.data.record.entity.Record()
    }
}
