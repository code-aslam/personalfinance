package com.example.personalfinance.data.datasource.local.sqldatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.personalfinance.data.accounts.dao.AccountDao
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.category.dao.CategoryDao
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.data.record.dao.RecordDao
import com.example.personalfinance.data.record.entity.Record

@Database(
    entities = [Record::class, Category::class, Account::class],
    version = 10
)
abstract class FinanceDataBase : RoomDatabase() {
    abstract val recordDao : RecordDao
    abstract val categoryDao : CategoryDao
    abstract val accountDao : AccountDao
}