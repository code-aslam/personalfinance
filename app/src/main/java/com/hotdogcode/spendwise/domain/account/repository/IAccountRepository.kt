package com.hotdogcode.spendwise.domain.account.repository

import com.hotdogcode.spendwise.data.accounts.entity.Account
import kotlinx.coroutines.flow.Flow

interface IAccountRepository {
    suspend fun addOrUpdateAccount(account: Account):Long

    suspend fun removeAccount(account: Account)

    fun fetchAccounts() : Flow<List<Account>>

}