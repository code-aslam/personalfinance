package com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.usecase

import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import kotlinx.coroutines.CoroutineScope

typealias UseCaseExecutorProvider =
        @JvmSuppressWildcards
        (coroutineScope : CoroutineScope) -> UseCaseExecutor