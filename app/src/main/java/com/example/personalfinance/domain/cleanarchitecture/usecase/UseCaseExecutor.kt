package com.example.personalfinance.domain.cleanarchitecture.usecase

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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