package com.example.personalfinance.presentation.records

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.personalfinance.common.TransactionType
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.data.record.entity.RecordWithCategoryAndAccount
import com.example.personalfinance.domain.account.usecases.AddOrUpdateAccountUseCase
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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
    useCaseExecutor: UseCaseExecutor,
    private val addOrUpdateRecordUseCase: AddOrUpdateRecordUseCase,
    private val addOrUpdateAccountUseCase: AddOrUpdateAccountUseCase
) : BaseViewModel(useCaseExecutor) {


    private val _dateSortedRecords = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())
    val dateSortedRecords = _dateSortedRecords.asStateFlow()


    init {
        fetchRecords()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchRecords(){
        viewModelScope.launch {
            useCaseExecutor.execute(
                getRecordsUseCase,
                Unit
            ){
                records ->
                viewModelScope.launch {
                    records.collect { list ->
                        _dateSortedRecords.value = groupDataByDaySorted(list).toMutableMap()
                    }
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun groupDataByDaySorted(dataList: List<RecordWithCategoryAndAccount>): Map<LocalDate, List<RecordWithCategoryAndAccount>> {
        return dataList.groupBy {
            val instant = Instant.ofEpochMilli(it.date)
            instant.atZone(ZoneId.systemDefault()).toLocalDate()
        }.toSortedMap { date1, date2 -> date2.compareTo(date1) }
    }


    private fun addNewRecordAction(record: Record, firstAccount: Account, secondAccount: Account ? = null) {
        viewModelScope.launch {
                useCaseExecutor.execute(addOrUpdateRecordUseCase, record){
                    when(record.transactionType){
                        TransactionType.INCOME -> {
                            firstAccount.balance += record.amount
                            updateAccount(firstAccount)
                        }
                        TransactionType.EXPANSE -> {
                            firstAccount.balance -= record.amount
                            updateAccount(firstAccount)
                        }
                        TransactionType.TRANSFER -> {}
                    }
                }
        }

    }

    private fun updateAccount(account: Account){
        viewModelScope.launch {
            useCaseExecutor.execute(addOrUpdateAccountUseCase, account){
            }
        }

    }

    fun addNewRecord(record: Record,firstAccount: Account, secondAccount: Account ? = null){
        addNewRecordAction(record, firstAccount, secondAccount)
    }





}