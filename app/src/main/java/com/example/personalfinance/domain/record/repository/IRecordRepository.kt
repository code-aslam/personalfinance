package com.example.personalfinance.domain.record.repository

import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.data.record.entity.Record
import kotlinx.coroutines.flow.Flow

interface IRecordRepository {
    fun fetchRecords() : Flow<List<Record>>

    suspend fun addOrUpdateRecord(record: Record):Long

    suspend fun removeRecord(record: Record)
}