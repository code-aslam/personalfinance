package com.example.personalfinance.data.category.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.personalfinance.data.category.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCategory(category: Category) : Long

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category")
    fun getAllCategories() : Flow<List<Category>>
}