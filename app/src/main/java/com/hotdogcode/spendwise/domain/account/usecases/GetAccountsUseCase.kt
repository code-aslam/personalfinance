package com.hotdogcode.spendwise.domain.account.usecases

import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.accounts.repository.AccountRepository
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
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