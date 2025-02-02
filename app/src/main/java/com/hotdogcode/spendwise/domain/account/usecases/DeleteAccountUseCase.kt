package com.hotdogcode.spendwise.domain.account.usecases

import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.accounts.repository.AccountRepository
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.BackgroundExecutingUsecase
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : BackgroundExecutingUsecase<Account,Unit>(){
    override suspend fun executeInBackground(request: Account) {
        accountRepository.removeAccount(request)
    }
}