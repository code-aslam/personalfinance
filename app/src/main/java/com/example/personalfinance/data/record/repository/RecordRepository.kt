package com.example.personalfinance.data.record.repository

import com.example.personalfinance.data.record.dao.RecordDao
import com.example.personalfinance.domain.home.repository.IHomeRepository
import com.example.personalfinance.data.record.entity.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRepository @Inject constructor(
    private val recordDao: RecordDao
) : IHomeRepository {
    override fun fetchRecords() : Flow<List<Record>> {
        return recordDao.getRecordList()
    }
}