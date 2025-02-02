package com.example.personalfinance.data.category.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.category.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateExistingCategory(category: Category)

    @Transaction
    suspend fun insertOrUpdateCategory(category: Category) : Long{
        val id = insertCategory(category)
        if (id == -1L) {
            updateExistingCategory(category)
        }
        return id
    }

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category")
    fun getAllCategories() : Flow<List<Category>>

    @Query("DELETE FROM category")
    suspend fun clearTable() // will delete all table data. DO NOT delete table
}