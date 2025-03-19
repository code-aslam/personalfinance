package com.hotdogcode.spendwise.data.category.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.CategoryType
import com.hotdogcode.spendwise.common.IconName

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id : Long =  0,
    var title :String,
    var icon : IconName,
    var type : CategoryType
){
    companion object{
        fun testData() = Category(
            id = -1,
            title = "Category",
            icon = IconName.CATEGORY_BIG,
            type = CategoryType.INCOME
        )
    }

}
