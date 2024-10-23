package com.example.personalfinance.data.home.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.personalfinance.data.home.entity.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Insert
    suspend fun insertRecord(record : Record)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("SELECT * FROM record")
    fun getRecordList() : Flow<List<Record>>
}

