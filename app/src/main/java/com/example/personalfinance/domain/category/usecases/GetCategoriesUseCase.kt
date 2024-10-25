package com.example.personalfinance.domain.category.usecases

import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.data.category.repository.CategoryRepository
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BackgroundExecutingUsecase<Unit, Flow<List<Category>>>() {
    override suspend fun executeInBackground(request: Unit): Flow<List<Category>> {
        return categoryRepository.fetchCategories()
    }
}