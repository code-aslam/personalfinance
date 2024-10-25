package com.example.personalfinance.domain.category.repository

import com.example.personalfinance.data.category.entity.Category
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    suspend fun addCategory(category: Category)

    suspend fun removeCategory(category: Category)

    fun fetchCategories() : Flow<List<Category>>
}