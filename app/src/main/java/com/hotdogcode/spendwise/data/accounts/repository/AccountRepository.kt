package com.hotdogcode.spendwise.data.accounts.repository

import com.hotdogcode.spendwise.data.accounts.dao.AccountDao
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.domain.account.repository.IAccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountDao: AccountDao
) : IAccountRepository {
    override suspend fun addOrUpdateAccount(account: Account): Long {
        return accountDao.insertOrUpdateAccount(account)
    }

    override suspend fun removeAccount(account: Account){
        return accountDao.deleteAccount(account)
    }

    override fun fetchAccounts(): Flow<List<Account>> {
        return accountDao.getAllAccounts()
    }
}