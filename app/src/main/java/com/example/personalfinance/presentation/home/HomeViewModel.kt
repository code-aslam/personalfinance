package com.example.personalfinance.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.domain.home.usecases.ClearAllTablesUseCase
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor,
    private val clearAllTablesUseCase: ClearAllTablesUseCase
) : BaseViewModel(useCaseExecutor) {

    fun clearAllTables(){
        viewModelScope.launch {
            useCaseExecutor.execute(clearAllTablesUseCase, Unit){
                showToast("Data Cleared")
            }
        }
    }
}