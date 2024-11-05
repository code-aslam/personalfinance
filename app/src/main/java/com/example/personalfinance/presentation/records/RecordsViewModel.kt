package com.example.personalfinance.presentation.records

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.domain.home.usecases.FetchRecordsUseCase
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.personalfinance.data.home.entity.Record
import com.example.personalfinance.domain.account.usecases.GetAccountsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Stack

import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val fetchRecordsUseCase: FetchRecordsUseCase,
    useCaseExecutor: UseCaseExecutor,
    private val getAllAccountsUseCase: GetAccountsUseCase,
) : BaseViewModel(useCaseExecutor) {


    private val _accountList = MutableStateFlow(mutableListOf<Account>())
    val accountList = _accountList.asStateFlow()

    private val _showCreateRecord = MutableStateFlow(false)
    var showCreateRecord: StateFlow<Boolean> = _showCreateRecord.asStateFlow()


    private val calculationStack = Stack<Char>()
    private val _result = MutableStateFlow("")
    var result : StateFlow<String> = _result.asStateFlow()

    private val _symbol = MutableStateFlow("")
    var symbol : StateFlow<String> = _symbol.asStateFlow()

    init {

    }

    fun getAccounts(){
        useCaseExecutor.execute(
            getAllAccountsUseCase,
            Unit,
            ::handleAccounts
        )
    }


    private fun handleAccounts(records: Flow<List<Account>>) {
        val accountList = _accountList.value
        viewModelScope.launch {
            records.collect { list ->
                list.forEach {account-> accountList.add(account)}
            }
            _accountList.value = accountList
        }

    }


    fun showCreateRecord(){
        _showCreateRecord.value = true
    }

    fun hideCreateRecord(){
        _showCreateRecord.value = false
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