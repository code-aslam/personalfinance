package com.hotdogcode.spendwise.domain.record.usecases

import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.domain.record.repository.IRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecordsUseCase @Inject constructor(
    private val repository: IRecordRepository,
) : BackgroundExecutingUsecase<Unit, Flow<List<RecordWithCategoryAndAccount>>>(
) {
    override suspend fun executeInBackground(request: Unit): Flow<List<RecordWithCategoryAndAccount>> = repository.fetchRecords()
}