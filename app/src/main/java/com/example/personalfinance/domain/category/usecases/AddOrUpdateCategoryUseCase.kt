package com.example.personalfinance.domain.category.usecases

import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.data.category.repository.CategoryRepository
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class AddOrUpdateCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
): BackgroundExecutingUsecase<Category, Long>() {
    override suspend fun executeInBackground(request: Category) : Long {
        return categoryRepository.addOrUpdateCategory(request)
    }
}