package com.example.personalfinance.presentation.createrecord

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class CreateRecordViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel(useCaseExecutor) {

    private val _result = MutableStateFlow("0")
    var result : StateFlow<String> = _result.asStateFlow()

    private val _symbol = MutableStateFlow("")
    var symbol : StateFlow<String> = _symbol.asStateFlow()

    private val _selectedAccount = MutableStateFlow(Account(
        name = "Account",
        icon = R.drawable.walletbig,
        initialAmount = 0
    ))
    val selectedAccount = _selectedAccount.asStateFlow()

    private val _selectedAccountForCategory = MutableStateFlow(Account(
        name = "Account",
        icon = R.drawable.walletbig,
        initialAmount = 0
    ))
    val selectedAccountForCategory = _selectedAccountForCategory.asStateFlow()

    private val _selectedCategory = MutableStateFlow(Category(
        title = "Category",
        icon = R.drawable.price,
        type = CategoryType.INCOME
    ))
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _transactionType = MutableStateFlow(false)
    val transactionType = _transactionType.asStateFlow()

    private var part1 = ""
    private var part2 = ""

    fun updateAccount(newAccount : Account){
        _selectedAccount.value = newAccount
    }

    fun updateCategory(newCategory : Category){
        _selectedCategory.value = newCategory
    }



    fun updateTransferType(newValue : Boolean){
        _transactionType.value = newValue
    }

    fun processDelete(){
        if(part2.isNotEmpty()){
            part2 = part2.dropLast(1)
            _result.value = part2
        }else if(part1.isNotEmpty()){
            part1 = part1.dropLast(1)
            _result.value = part1
        }else{
            _result.value = _result.value.dropLast(1)
        }
    }

    fun processCalculation(toProcess : Char){
        if(isSymbol(toProcess)){
            if(part1.isNotEmpty() && part2.isNotEmpty()){
                _result.value = calculate(part1,part2, _symbol.value[0]).toString()
                part1 = _result.value
                part2 = ""
            }else if(part1.isEmpty() && part2.isEmpty()){
                return
            }
            if(toProcess == '=') {
                if(_symbol.value == "" || _symbol.value == "=") return
                part1 = ""
                part2 = ""
                _symbol.value = ""
                return
            }
            _symbol.value = toProcess.toString()
        }else{
            if(_symbol.value == "" || _symbol.value == "="){
                part1 += toProcess
                _result.value = part1
            }else{
                part2 += toProcess
                _result.value = part2
            }
        }
    }

    private fun calculate(p1 : String, p2 : String, operator : Char) : Double {
        val num1 = p1.toDouble()
        val num2 = p2.toDouble()
        return when (operator) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> num1 / num2
            else -> {0.0}
        }
    }
    private fun isSymbol(toProcess: Char) : Boolean{
        return (toProcess == '+' || toProcess == '-' || toProcess == '*' || toProcess == '/' || toProcess == '=')
    }

}