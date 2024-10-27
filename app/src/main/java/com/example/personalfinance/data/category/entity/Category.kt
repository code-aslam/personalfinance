package com.example.personalfinance.data.category.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.personalfinance.common.CategoryType

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id : Long =  0,
    var title :String,
    val icon : Int,
    var type : CategoryType
)
