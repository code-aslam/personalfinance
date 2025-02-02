package com.hotdogcode.spendwise.data.home.repository

import com.hotdogcode.spendwise.data.accounts.dao.AccountDao
import com.hotdogcode.spendwise.data.category.dao.CategoryDao
import com.hotdogcode.spendwise.data.record.dao.RecordDao
import com.hotdogcode.spendwise.domain.home.repository.IHomeRepository
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