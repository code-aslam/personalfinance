package com.hotdogcode.spendwise.domain.category.usecases

import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.data.category.repository.CategoryRepository
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class AddOrUpdateCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
): BackgroundExecutingUsecase<Category, Long>() {
    override suspend fun executeInBackground(request: Category) : Long {
        return categoryRepository.addOrUpdateCategory(request)
    }
}