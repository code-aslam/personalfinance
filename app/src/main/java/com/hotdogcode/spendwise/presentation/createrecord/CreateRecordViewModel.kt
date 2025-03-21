package com.hotdogcode.spendwise.presentation.createrecord

import androidx.compose.material3.Icon
import androidx.lifecycle.viewModelScope
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.CategoryType
import com.hotdogcode.spendwise.common.IconName
import com.hotdogcode.spendwise.common.toRequiredFormat
import com.hotdogcode.spendwise.common.toRequiredTimeFormat
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
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
        initialAmount = 0.0,
        icon = IconName.WALLET_BIG
    ))
    val selectedAccount = _selectedAccount.asStateFlow()


    private val _selectedCategory = MutableStateFlow(Category(
        title = "Category",
        icon = IconName.CATEGORY_BIG,
        type = CategoryType.INCOME
    ))
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _transactionType = MutableStateFlow(false)
    val transactionType = _transactionType.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    var showDatePicker = _showDatePicker.asStateFlow()

    private val _showTimePicker = MutableStateFlow(false)
    var showTimePicker = _showTimePicker.asStateFlow()

    private val _selectedDate = MutableStateFlow(Date().toRequiredFormat())
    var selectedDate = _selectedDate.asStateFlow()

    private val _selectedDateMills = MutableStateFlow(System.currentTimeMillis())
    var selectedDateMills = _selectedDateMills.asStateFlow()

    private val _selectedTime = MutableStateFlow(Date().toRequiredTimeFormat())
    var selectedTime = _selectedTime.asStateFlow()

    private var part1 = ""
    private var part2 = ""

    private val _selectedRecordId = MutableStateFlow(-1L)
    var selectedRecordId = _selectedRecordId.asStateFlow()

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

    fun setDatePicker(show : Boolean){
        _showDatePicker.value = show
    }

    fun setTimePicker(show : Boolean){
        _showTimePicker.value = show
    }

    fun updateDate(newDateMills : Long?){
        newDateMills?.let {
           _selectedDate.value = Date(it).toRequiredFormat()
            _selectedDateMills.value = it
        }
    }

    fun updateTime(newDate: String){
        _selectedTime.value = newDate
    }


    fun updateRecordId(newId : Long){
        _selectedRecordId.value = newId
    }

    fun updateResult(newResult : String){
        viewModelScope.launch {
            for(symbol in newResult){
                processCalculation(symbol)
            }
        }

    }
}