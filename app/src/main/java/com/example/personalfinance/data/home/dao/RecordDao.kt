package com.example.personalfinance.data.home.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.personalfinance.data.home.entity.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun insertRecord(record : Record)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("SELECT * FROM record")
    suspend fun getRecordList() : Flow<List<Record>>
}