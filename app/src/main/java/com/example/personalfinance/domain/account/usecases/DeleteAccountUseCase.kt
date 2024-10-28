package com.example.personalfinance.domain.account.usecases

import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.accounts.repository.AccountRepository
import com.example.personalfinance.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : BackgroundExecutingUsecase<Account,Unit>(){
    override suspend fun executeInBackground(request: Account) {
        accountRepository.removeAccount(request)
    }
}