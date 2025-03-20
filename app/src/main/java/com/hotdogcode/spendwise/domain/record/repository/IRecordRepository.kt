package com.hotdogcode.spendwise.domain.record.repository

import com.hotdogcode.spendwise.data.record.entity.Record
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import kotlinx.coroutines.flow.Flow

interface IRecordRepository {
    fun fetchRecords() : Flow<List<RecordWithCategoryAndAccount>>

    fun fetchRecord(recordId : Long) : Flow<RecordWithCategoryAndAccount>

    suspend fun addOrUpdateRecord(record: Record):Long

    suspend fun removeRecord(record: Record)

    suspend fun deleteRecordWithId(recordId: Long)

    fun fetchRecordForAccount(accountId: Long) : Flow<List<RecordWithCategoryAndAccount>>

    fun fetchRecordForCategory(categoryId: Long) : Flow<List<RecordWithCategoryAndAccount>>

}