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

    override fun fetchRecord(recordId: Long): Flow<RecordWithCategoryAndAccount> {
        return recordDao.getRecord(recordId)
    }

    override suspend fun addOrUpdateRecord(record: Record): Long {
        return recordDao.insertOrUpdateRecord(record)
    }

    override suspend fun removeRecord(record: Record) {
        recordDao.deleteRecord(record)
    }

    override fun fetchRecordForAccount(accountId: Long): Flow<List<RecordWithCategoryAndAccount>> {
        return recordDao.getRecordListForAccount(accountId)
    }

    override fun fetchRecordForCategory(categoryId: Long): Flow<List<RecordWithCategoryAndAccount>> {
        return recordDao.getRecordListForCategory(categoryId)
    }

    override suspend fun deleteRecordWithId(recordId: Long) {
        recordDao.deleteRecordWithId(recordId)
    }
}