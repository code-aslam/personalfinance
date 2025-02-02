package com.hotdogcode.spendwise.domain.cleanarchitecture.usecase

abstract class BackgroundExecutingUsecase<REQUEST, RESULT>(
): UseCase<REQUEST, RESULT> {
    final override suspend fun execute(input: REQUEST, onResult: (RESULT) -> Unit) {
//        val result = withContext(Dispatchers.IO){
//            executeInBackground(input)
//        }

        onResult(executeInBackground(input))

    }

    abstract suspend fun executeInBackground(
        request: REQUEST
    ) : RESULT
}