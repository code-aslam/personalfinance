package com.example.personalfinance.data.record.repository

import com.example.personalfinance.data.record.dao.RecordDao
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.domain.record.repository.IRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordDao: RecordDao
) : IRecordRepository {
    override fun fetchRecords() : Flow<List<Record>> {
        return recordDao.getRecordList()
    }


    override suspend fun addOrUpdateRecord(record: Record): Long {
        return recordDao.insertRecord(record)
    }

    override suspend fun removeRecord(record: Record) {
        recordDao.deleteRecord(record)
    }
}