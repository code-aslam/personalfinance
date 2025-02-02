package com.example.personalfinance.domain.home.usecases

import com.example.personalfinance.data.home.repository.HomeRepository
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class ClearAllTablesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) : BackgroundExecutingUsecase<Unit, Unit>(){
    override suspend fun executeInBackground(request: Unit) {
        homeRepository.clearAllTables()
    }
}