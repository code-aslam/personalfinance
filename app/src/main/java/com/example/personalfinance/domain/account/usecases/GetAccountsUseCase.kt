package com.example.personalfinance.domain.account.usecases

import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.accounts.repository.AccountRepository
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : BackgroundExecutingUsecase<Unit, Flow<List<Account>>>() {
    override suspend fun executeInBackground(request: Unit): Flow<List<Account>> {
        return accountRepository.fetchAccounts()
    }

    suspend fun test():Flow<List<Account>>{
        return accountRepository.fetchAccounts()
    }
}