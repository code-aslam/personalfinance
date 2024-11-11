package com.example.personalfinance.domain.record.usecases

import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.domain.record.repository.IRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecordsUseCase @Inject constructor(
    private val repository: IRecordRepository,
) : BackgroundExecutingUsecase<Unit, Flow<List<Record>>>(
) {
    override suspend fun executeInBackground(request: Unit): Flow<List<Record>> = repository.fetchRecords()
}