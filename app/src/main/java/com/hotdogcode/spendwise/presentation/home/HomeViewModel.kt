package com.hotdogcode.spendwise.presentation.home

import androidx.lifecycle.viewModelScope
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.domain.home.usecases.ClearAllTablesUseCase
import com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.BaseViewModel
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