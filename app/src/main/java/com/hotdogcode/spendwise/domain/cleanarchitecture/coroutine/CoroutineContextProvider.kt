package com.hotdogcode.spendwise.domain.cleanarchitecture.coroutine

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val main : CoroutineContext
    val io : CoroutineContext
}