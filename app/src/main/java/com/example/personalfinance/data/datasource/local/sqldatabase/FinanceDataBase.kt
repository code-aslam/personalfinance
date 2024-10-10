package com.example.personalfinance.data.datasource.local.sqldatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.personalfinance.data.home.dao.RecordDao
import com.example.personalfinance.data.home.entity.Record

@Database(
    entities = [Record::class],
    version = 1
)
abstract class FinanceDataBase : RoomDatabase() {
    abstract val recordDao : RecordDao
}