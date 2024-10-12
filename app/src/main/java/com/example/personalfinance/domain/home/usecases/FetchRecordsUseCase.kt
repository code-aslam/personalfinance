package com.example.personalfinance.domain.home.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import com.example.personalfinance.domain.home.repository.IHomeRepository
import com.example.personalfinance.data.home.entity.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchRecordsUseCase @Inject constructor(
    private val repository: IHomeRepository,
) : BackgroundExecutingUsecase<String, Flow<List<Record>>>(
) {
    override fun executeInBackground(request: String): Flow<List<Record>> = repository.fetchRecords()
}