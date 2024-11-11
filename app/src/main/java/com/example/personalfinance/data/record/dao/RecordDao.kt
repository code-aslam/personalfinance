package com.example.personalfinance.data.record.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.personalfinance.data.record.entity.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun insertRecord(record : Record) : Long

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("SELECT * FROM record")
    fun getRecordList() : Flow<List<Record>>
}

