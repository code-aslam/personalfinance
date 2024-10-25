package com.example.personalfinance.domain.category.usecases

import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.data.category.repository.CategoryRepository
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
): BackgroundExecutingUsecase<Category, Unit>() {
    override suspend fun executeInBackground(request: Category) {
        categoryRepository.addCategory(request)
    }
}