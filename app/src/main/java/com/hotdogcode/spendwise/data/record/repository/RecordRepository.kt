package com.hotdogcode.spendwise.data.record.repository

import com.hotdogcode.spendwise.data.record.dao.RecordDao
import com.hotdogcode.spendwise.data.record.entity.Record
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.domain.record.repository.IRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordDao: RecordDao
) : IRecordRepository {
    override fun fetchRecords() : Flow<List<RecordWithCategoryAndAccount>> {
        return recordDao.getRecordList()
    }


    override suspend fun addOrUpdateRecord(record: Record): Long {
        return recordDao.insertRecord(record)
    }

    override suspend fun removeRecord(record: Record) {
        recordDao.deleteRecord(record)
    }
}