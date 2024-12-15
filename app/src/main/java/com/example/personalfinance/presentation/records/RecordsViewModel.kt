package com.example.personalfinance.presentation.records

import androidx.lifecycle.viewModelScope
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.data.record.entity.RecordWithCategoryAndAccount
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.domain.record.usecases.AddOrUpdateRecordUseCase
import com.example.personalfinance.domain.record.usecases.GetRecordsUseCase
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
    useCaseExecutor: UseCaseExecutor,
    private val addOrUpdateRecordUseCase: AddOrUpdateRecordUseCase
) : BaseViewModel(useCaseExecutor) {


    private val _recordWithCategoryAndAccountList = MutableStateFlow(mutableListOf<RecordWithCategoryAndAccount>())
    val recordWithCategoryAndAccountList = _recordWithCategoryAndAccountList.asStateFlow()

    init {
        fetchRecords()
    }

    fun fetchRecords(){
        viewModelScope.launch {
            useCaseExecutor.execute(
                getRecordsUseCase,
                Unit
            ){
                records ->
                viewModelScope.launch {
                    records.collect { list ->
                        _recordWithCategoryAndAccountList.value = list.toMutableList()
                    }
                }
            }
        }

    }


    private fun addNewRecordAction(record: Record) {
        viewModelScope.launch {
            useCaseExecutor.execute(addOrUpdateRecordUseCase, record){
            }
        }

    }

    fun addNewRecord(record: Record){
        addNewRecordAction(record)
    }





}