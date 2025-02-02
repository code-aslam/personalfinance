package com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    val useCaseExecutor: UseCaseExecutor
): ViewModel() {
    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    protected fun showToast(message : String){
        viewModelScope.launch {
            _toastEvent.emit(message)
        }
    }
}