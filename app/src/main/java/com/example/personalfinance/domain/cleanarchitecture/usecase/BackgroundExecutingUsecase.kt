package com.example.personalfinance.domain.cleanarchitecture.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BackgroundExecutingUsecase<REQUEST, RESULT>(
): UseCase<REQUEST, RESULT> {
    final override suspend fun execute(input: REQUEST, onResult: (RESULT) -> Unit) {
        val result = withContext(Dispatchers.IO){
            executeInBackground(input)
        }

        onResult(result)
    }

    abstract fun executeInBackground(
        request: REQUEST
    ) : RESULT
}