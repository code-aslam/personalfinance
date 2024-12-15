package com.example.personalfinance.domain.account.usecases

import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.accounts.repository.AccountRepository
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class AddOrUpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : BackgroundExecutingUsecase<Account, Long>() {
    override suspend fun executeInBackground(request: Account): Long {
        return accountRepository.addOrUpdateAccount(request)
    }

    suspend fun test(request: Account):Long{
        return accountRepository.addOrUpdateAccount(request)
    }
}