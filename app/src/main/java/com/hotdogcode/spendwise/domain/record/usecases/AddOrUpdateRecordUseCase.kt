package com.hotdogcode.spendwise.domain.record.usecases

import com.hotdogcode.spendwise.data.record.entity.Record
import com.hotdogcode.spendwise.data.record.repository.RecordRepository
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class AddOrUpdateRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
): BackgroundExecutingUsecase<Record, Long>() {
    override suspend fun executeInBackground(request: Record) : Long {
        return recordRepository.addOrUpdateRecord(request)
    }
}