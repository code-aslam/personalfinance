package com.hotdogcode.spendwise.domain.category.repository

import com.hotdogcode.spendwise.data.category.entity.Category
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    suspend fun addOrUpdateCategory(category: Category):Long

    suspend fun removeCategory(category: Category)

    fun fetchCategories() : Flow<List<Category>>
}