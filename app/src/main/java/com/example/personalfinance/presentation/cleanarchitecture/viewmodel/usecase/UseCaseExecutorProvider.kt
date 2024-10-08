package com.example.personalfinance.presentation.cleanarchitecture.viewmodel.usecase

import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import kotlinx.coroutines.CoroutineScope

typealias UseCaseExecutorProvider =
        @JvmSuppressWildcards
        (coroutineScope : CoroutineScope) -> UseCaseExecutor