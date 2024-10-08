package com.example.personalfinance.presentation.cleanarchitecture.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.usecase.UseCaseExecutorProvider

abstract class BaseViewModel(
    val useCaseExecutor: UseCaseExecutor
): ViewModel() {

}