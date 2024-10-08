package com.example.personalfinance.domain.cleanarchitecture.coroutine

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val main : CoroutineContext
    val io : CoroutineContext
}