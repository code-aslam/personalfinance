package com.hotdogcode.spendwise.domain.record.usecases

import com.hotdogcode.spendwise.data.record.repository.RecordRepository
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject


class DeleteRecordWithIdUseCase @Inject constructor(
    private val recordRepository: RecordRepository
): BackgroundExecutingUsecase<Long, Unit>() {
    override suspend fun executeInBackground(request: Long) {
        recordRepository.deleteRecordWithId(request)
    }
}