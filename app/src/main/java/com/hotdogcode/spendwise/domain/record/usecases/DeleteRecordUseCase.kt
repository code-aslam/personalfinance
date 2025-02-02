package com.hotdogcode.spendwise.domain.record.usecases
import com.hotdogcode.spendwise.data.record.entity.Record
import com.hotdogcode.spendwise.data.record.repository.RecordRepository
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class DeleteRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
): BackgroundExecutingUsecase<Record, Unit>() {
    override suspend fun executeInBackground(request: Record) {
        recordRepository.removeRecord(request)
    }
}