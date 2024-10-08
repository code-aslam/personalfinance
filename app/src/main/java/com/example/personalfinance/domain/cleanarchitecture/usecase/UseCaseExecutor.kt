package com.example.personalfinance.domain.cleanarchitecture.usecase

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UseCaseExecutor @Inject constructor(
    private val coroutineScope: CoroutineScope
) {
    fun <INPUT, OUTPUT> execute(
        useCase: UseCase<INPUT, OUTPUT>,
        value : INPUT,
        onResult: (OUTPUT) -> Unit = {}
    ){
        coroutineScope.launch {
            try{
                useCase.execute(value, onResult)
            }catch (ignore : CancellationException){

            }catch (throwable : Throwable){

            }
        }
    }
}