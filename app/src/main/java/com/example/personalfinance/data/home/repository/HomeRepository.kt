package com.example.personalfinance.data.home.repository

import com.example.personalfinance.data.accounts.dao.AccountDao
import com.example.personalfinance.data.category.dao.CategoryDao
import com.example.personalfinance.data.record.dao.RecordDao
import com.example.personalfinance.domain.home.repository.IHomeRepository
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val accountDao: AccountDao,
    private val recordDao: RecordDao
): IHomeRepository {
    override suspend fun clearAllTables() {
        categoryDao.clearTable()
        accountDao.clearTable()
        recordDao.clearTable()
    }
}