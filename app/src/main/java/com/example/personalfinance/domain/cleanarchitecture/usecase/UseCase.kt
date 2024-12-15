package com.example.personalfinance.domain.cleanarchitecture.usecase

import kotlinx.coroutines.CoroutineScope

interface UseCase<REQUEST, RESULT> {
    suspend fun execute(input : REQUEST, onResult : (RESULT) -> Unit)
}