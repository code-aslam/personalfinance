package com.hotdogcode.spendwise.data.category.repository

import com.hotdogcode.spendwise.data.category.dao.CategoryDao
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.domain.category.repository.ICategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) : ICategoryRepository{
    override suspend fun addOrUpdateCategory(category: Category) : Long {
        return categoryDao.insertOrUpdateCategory(category)
    }

    override suspend fun removeCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    override fun fetchCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }
}