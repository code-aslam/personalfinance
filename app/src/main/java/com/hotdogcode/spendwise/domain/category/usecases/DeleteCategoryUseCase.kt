package com.hotdogcode.spendwise.domain.category.usecases

import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.data.category.repository.CategoryRepository
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
): BackgroundExecutingUsecase<Category, Unit>() {
    override suspend fun executeInBackground(request: Category) {
        categoryRepository.removeCategory(request)
    }
}