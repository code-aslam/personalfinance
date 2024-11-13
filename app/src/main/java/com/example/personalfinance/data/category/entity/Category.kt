package com.example.personalfinance.data.category.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id : Long =  0,
    var title :String,
    var icon : Int,
    var type : CategoryType
){
    companion object{
        fun testData() = Category(
            id = -1,
            title = "Category",
            icon = R.drawable.price,
            type = CategoryType.INCOME
        )
    }

}
