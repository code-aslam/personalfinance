package com.hotdogcode.spendwise.data.record.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.record.entity.Record
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record : Record) : Long

    @Update
    suspend fun updateExistingAccount(record: Record)

    @Transaction
    suspend fun insertOrUpdateRecord(record: Record) : Long{
        val id = insert(record)
        if (id == -1L) {
            updateExistingAccount(record)
        }
        return id
    }

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
            Account.type AS accountType,
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
        WHERE Record.id = :recordId
    """)
    fun getRecord(recordId : Long) : Flow<RecordWithCategoryAndAccount>

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
            Account.type AS accountType,
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
            Account.type AS accountType,
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
        WHERE Record.accountId = :accountId
    """)
    fun getRecordListForAccount(accountId: Long) : Flow<List<RecordWithCategoryAndAccount>>

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
            Account.type AS accountType,
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
        WHERE Record.categoryId = :categoryId
    """)
    fun getRecordListForCategory(categoryId: Long) : Flow<List<RecordWithCategoryAndAccount>>

    @Query("DELETE FROM account")
    suspend fun clearTable() // will delete all table data. DO NOT delete table

    @Query("DELETE FROM Record WHERE id = :recordId")
    suspend fun deleteRecordWithId(recordId: Long)
}

