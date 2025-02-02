package com.hotdogcode.spendwise.domain.home.usecases

import com.hotdogcode.spendwise.data.home.repository.HomeRepository
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class ClearAllTablesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) : BackgroundExecutingUsecase<Unit, Unit>(){
    override suspend fun executeInBackground(request: Unit) {
        homeRepository.clearAllTables()
    }
}