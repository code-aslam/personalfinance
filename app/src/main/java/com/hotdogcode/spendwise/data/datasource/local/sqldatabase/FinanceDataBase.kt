package com.hotdogcode.spendwise.data.datasource.local.sqldatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hotdogcode.spendwise.data.accounts.dao.AccountDao
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.category.dao.CategoryDao
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.data.record.dao.RecordDao
import com.hotdogcode.spendwise.data.record.entity.Record

@Database(
    entities = [Record::class, Category::class, Account::class],
    version = 13
)
abstract class FinanceDataBase : RoomDatabase() {
    abstract val recordDao : RecordDao
    abstract val categoryDao : CategoryDao
    abstract val accountDao : AccountDao
}