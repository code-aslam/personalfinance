package com.hotdogcode.spendwise.domain.category.usecases

import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.data.category.repository.CategoryRepository
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BackgroundExecutingUsecase<Unit, Flow<List<Category>>>() {
    override suspend fun executeInBackground(request: Unit): Flow<List<Category>> {
        return categoryRepository.fetchCategories()
    }
}