package com.example.personalfinance.data.record.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.data.record.entity.RecordWithCategoryAndAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun insertRecord(record : Record) : Long

    @Delete
    suspend fun deleteRecord(record: Record)

    @Query("""
        SELECT 
            Record.id AS recordId,
            Record.amount,
            Record.date,
            Record.time,
            Record.notes,
            Record.transactionType,
            Record.accountId,
            Account.initialAmount AS accountInitialAmount,
            Account.icon AS accountIcon,
            Account.name AS accountName,
            Record.categoryId,
            Category.title AS categoryTitle,
            Category.type AS categoryType,
            Category.icon AS categoryIcon
        FROM 
            Record
        INNER JOIN 
            Account ON Record.accountId = Account.id
        INNER JOIN 
            Category ON Record.categoryId = Category.id
    """)
    fun getRecordList() : Flow<List<RecordWithCategoryAndAccount>>


    @Query("DELETE FROM account")
    suspend fun clearTable() // will delete all table data. DO NOT delete table
}

