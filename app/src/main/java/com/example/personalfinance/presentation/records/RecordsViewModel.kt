package com.example.personalfinance.presentation.records

import androidx.lifecycle.viewModelScope
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.domain.record.usecases.GetRecordsUseCase
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.personalfinance.domain.account.usecases.GetAccountsUseCase
import com.example.personalfinance.domain.record.usecases.AddOrUpdateRecordUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Stack

import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
    useCaseExecutor: UseCaseExecutor,
    private val addOrUpdateRecordUseCase: AddOrUpdateRecordUseCase
) : BaseViewModel(useCaseExecutor) {


    private val _recordList = MutableStateFlow(mutableListOf<Record>())
    val recordList = _recordList.asStateFlow()

    private val _showCreateRecord = MutableStateFlow(false)
    var showCreateRecord: StateFlow<Boolean> = _showCreateRecord.asStateFlow()


    init {
        fetchRecords()
    }


    private fun fetchRecords(){
        useCaseExecutor.execute(
            getRecordsUseCase,
            Unit,
            ::handleRecords
        )
    }


    private fun handleRecords(records: Flow<List<Record>>) {
        val recordList = _recordList.value
        _recordList.value.clear()
        viewModelScope.launch {
            records.collect { list ->
                list.forEach {record-> recordList.add(record)}
            }
            _recordList.value = recordList
        }

    }


    private fun addNewRecordAction(record: Record) {
        val records = _recordList.value.toMutableList()
        useCaseExecutor.execute(addOrUpdateRecordUseCase, record){
            record.id = it
            records.add(record)
            _recordList.value = records
        }
    }

    fun addNewRecord(record: Record){
        addNewRecordAction(record)
    }





}