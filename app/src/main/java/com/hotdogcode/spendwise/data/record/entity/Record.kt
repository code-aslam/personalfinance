package com.hotdogcode.spendwise.data.record.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.category.entity.Category

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
        fun getTest()  = com.hotdogcode.spendwise.data.record.entity.Record()
    }
}
