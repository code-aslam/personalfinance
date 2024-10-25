package com.example.personalfinance.data.category.repository

import com.example.personalfinance.data.category.dao.CategoryDao
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.domain.category.repository.ICategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) : ICategoryRepository{
    override suspend fun addCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    override suspend fun removeCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    override fun fetchCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }
}