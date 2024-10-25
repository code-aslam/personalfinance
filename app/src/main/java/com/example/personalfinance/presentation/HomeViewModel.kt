package com.example.personalfinance.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.domain.home.usecases.FetchRecordsUseCase
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.personalfinance.data.home.entity.Record
import com.example.personalfinance.presentation.categories.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchRecordsUseCase: FetchRecordsUseCase,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel(useCaseExecutor) {



    private val _recordList = MutableStateFlow(emptyList<Record>())
    val recordList: StateFlow<List<Record>> = _recordList

    init {

    }

    fun test() {
        useCaseExecutor.execute(fetchRecordsUseCase, "", ::pot)
    }

    fun pot(records: Flow<List<Record>>) {
        viewModelScope.launch {
            records.collect {
                _recordList.value = it
            }
        }
    }




}