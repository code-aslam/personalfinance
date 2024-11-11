package com.example.personalfinance.domain.record.usecases

import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.data.record.repository.RecordRepository
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class AddOrUpdateRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
): BackgroundExecutingUsecase<Record, Long>() {
    override suspend fun executeInBackground(request: Record) : Long {
        return recordRepository.addOrUpdateRecord(request)
    }
}