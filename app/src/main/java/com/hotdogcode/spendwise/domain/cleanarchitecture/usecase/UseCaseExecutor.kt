package com.hotdogcode.spendwise.domain.cleanarchitecture.usecase

import javax.inject.Inject

class UseCaseExecutor @Inject constructor(
) {
    suspend fun <INPUT, OUTPUT> execute(
        useCase: UseCase<INPUT, OUTPUT>,
        value : INPUT,
        onResult: (OUTPUT) -> Unit = {}
    ){
        useCase.execute(value, onResult)
    }
}