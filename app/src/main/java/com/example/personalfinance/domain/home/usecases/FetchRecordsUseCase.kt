package com.example.personalfinance.domain.home.usecases

import com.example.personalfinance.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import com.example.personalfinance.domain.home.repository.IHomeRepository
import com.example.personalfinance.domain.home.models.Record
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FetchRecordsUseCase @Inject constructor(
    private val repository: IHomeRepository,
) : BackgroundExecutingUsecase<String, List<Record>>(
) {
    override fun executeInBackground(request: String): List<Record>  = repository.fetchRecords()

}