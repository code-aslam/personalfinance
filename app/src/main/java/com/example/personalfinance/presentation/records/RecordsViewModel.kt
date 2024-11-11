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


    private val calculationStack = Stack<Char>()
    private val _result = MutableStateFlow("")
    var result : StateFlow<String> = _result.asStateFlow()

    private val _symbol = MutableStateFlow("")
    var symbol : StateFlow<String> = _symbol.asStateFlow()

    init {
        fetchRecords()
    }

    fun update(){
        _result.value = "test"
    }

    fun fetchRecords(){
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


    fun processCalculation(toProcess : Char){
        if(isSymbol(toProcess)){
            _symbol.value = toProcess.toString()
            if(calculationStack.isNotEmpty() && isSymbol(calculationStack.peek())){
                calculationStack.pop()
                calculationStack.push(toProcess)
                return
            }
            var text = ""
            val list = mutableListOf<String>()
            var symbol = 'a'
            while(calculationStack.isNotEmpty()){
                if(isSymbol(calculationStack.peek())){
                    list.add(text.reversed())
                    symbol = calculationStack.pop()
                    text = ""
                }else{
                    text += calculationStack.pop()
                }
            }
            if(text.isNotEmpty())
                list.add(text.reversed())
            if(isSymbol(symbol) || (list.size>1 && list[1].isNotEmpty() && list[0].isNotEmpty())){
                val n2 = list[0].toInt()
                val n1 = list[1].toInt()
                _result.value = when(symbol) {
                    '+' -> n1 + n2
                    '-' -> n1 - n2
                    '*' -> n1 * n2
                    '/' -> n1 / n2
                    else -> 0
                }.toString()

                for(ch in _result.value){
                    calculationStack.push(ch)
                }
            }else{
                for(ch in list[0]){
                    calculationStack.push(ch)
                }
            }
            calculationStack.push(toProcess)

        }else{
            if(calculationStack.isNotEmpty() && isSymbol(calculationStack.peek())){
                _result.value = toProcess.toString()
            }else{
                _result.value += toProcess
            }
            calculationStack.push(toProcess)
        }
    }

    private fun isSymbol(toProcess: Char) : Boolean{
        return (toProcess == '+' || toProcess == '-' || toProcess == '*' || toProcess == '/' || toProcess == '=')
    }

}