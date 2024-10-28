package com.example.personalfinance.data.accounts.repository

import com.example.personalfinance.data.accounts.dao.AccountDao
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.domain.account.repository.IAccountRepository
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