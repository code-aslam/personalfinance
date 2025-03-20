package com.hotdogcode.spendwise.presentation.records

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.record.entity.Record
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.domain.account.usecases.AddOrUpdateAccountUseCase
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.domain.record.usecases.AddOrUpdateRecordUseCase
import com.hotdogcode.spendwise.domain.record.usecases.DeleteRecordWithIdUseCase
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordForAccountUseCase
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordForCategoryUseCase
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordUseCase
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordsUseCase
import com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
    private val getRecordUseCase : GetRecordUseCase,
    private val deleteRecordWithIdUseCase: DeleteRecordWithIdUseCase,
    useCaseExecutor: UseCaseExecutor,
    private val addOrUpdateRecordUseCase: AddOrUpdateRecordUseCase,
    private val addOrUpdateAccountUseCase: AddOrUpdateAccountUseCase,
    private val getRecordForAccountUseCase: GetRecordForAccountUseCase,
    private val getRecordForCategoryUseCase: GetRecordForCategoryUseCase
) : BaseViewModel(useCaseExecutor) {


    private val _dateSortedRecords = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())
    val dateSortedRecords = _dateSortedRecords.asStateFlow()

    private val _dateSortedRecordsPerMonth = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())
    val dateSortedRecordsPerMonth = _dateSortedRecordsPerMonth.asStateFlow()

    private val _dateSortedRecordsForAccount = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())
    val dateSortedRecordsForAccount = _dateSortedRecordsForAccount.asStateFlow()

    private val _dateSortedRecordsForCategory = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())
    val dateSortedRecordsForCategory = _dateSortedRecordsForCategory.asStateFlow()

    private val _showDetails = MutableStateFlow(false)
    var showDetails = _showDetails.asStateFlow()

    private val _selectedRecordDetails = MutableStateFlow(RecordWithCategoryAndAccount())
    var selectedRecordDetails = _selectedRecordDetails.asStateFlow()

    private val _showDelete = MutableStateFlow(false)
    var showDelete = _showDelete.asStateFlow()

    private val _currentDate = MutableStateFlow(Calendar.getInstance())
    var currentDate = _currentDate.asStateFlow()
    init {
        fetchRecords()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchRecords(){
        viewModelScope.launch {
            useCaseExecutor.execute(
                getRecordsUseCase,
                Unit
            ){
                records ->
                viewModelScope.launch {
                    records.collect { list ->
                        _dateSortedRecords.value = groupDataByDaySorted(list).toMutableMap()
                         updateData()
                    }
                }
            }
        }

    }

    fun updateCurrentMonth(months : Int){
        val newDate = _currentDate.value.clone() as Calendar // Create a new instance
        newDate.add(Calendar.MONTH, months) // Modify the new instance
        _currentDate.value = newDate // Update the state with the new instance
        updateData()
    }

    private fun updateData(){
        _dateSortedRecordsPerMonth.value = filterRecordsByMonth(_dateSortedRecords.value, _currentDate.value.time).toMutableMap()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun groupDataByDaySorted(dataList: List<RecordWithCategoryAndAccount>): Map<LocalDate, List<RecordWithCategoryAndAccount>> {
        return dataList.groupBy {
            val instant = Instant.ofEpochMilli(it.date)
            instant.atZone(ZoneId.systemDefault()).toLocalDate()
        }.toSortedMap { date1, date2 -> date2.compareTo(date1) }
    }

    private fun filterRecordsByMonth(
        recordsMap: Map<LocalDate, List<RecordWithCategoryAndAccount>>,
        date: Date
    ): Map<LocalDate, List<RecordWithCategoryAndAccount>> {
        // Convert the input Date to LocalDate
        val inputLocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        // Filter the map to include only entries for the same month and year
        return recordsMap.filter { (key, _) ->
            key.year == inputLocalDate.year && key.month == inputLocalDate.month
        }.toSortedMap { date1, date2 -> date2.compareTo(date1) }
    }


    fun getRecordsForAccount(accountId: Long){
        viewModelScope.launch {
            useCaseExecutor.execute(
                getRecordForAccountUseCase,
                accountId
            ){
                    records ->
                viewModelScope.launch {
                    records.collect { list ->
                        _dateSortedRecordsForAccount.value = groupDataByDaySorted(list).toMutableMap()
                    }
                }
            }
        }
    }

    fun getRecord(recordId : Long){
        viewModelScope.launch {
            useCaseExecutor.execute(
                getRecordUseCase,
                recordId
            ){
                record ->
                viewModelScope.launch {
                    record.collect {
                        _selectedRecordDetails.value = it
                    }
                }
                }
        }
    }

    fun getRecordsForCategory(categoryId: Long){
        viewModelScope.launch {
            useCaseExecutor.execute(
                getRecordForCategoryUseCase,
                categoryId
            ){
                    records ->
                viewModelScope.launch {
                    records.collect { list ->
                        _dateSortedRecordsForCategory.value = groupDataByDaySorted(list).toMutableMap()
                    }
                }
            }
        }
    }

    private fun addNewRecordAction(record: Record, firstAccount: Account, secondAccount: Account ? = null) {
        viewModelScope.launch {
                useCaseExecutor.execute(addOrUpdateRecordUseCase, record){
                    when(record.transactionType){
                        TransactionType.INCOME -> {
                            firstAccount.balance += record.amount
                            updateAccount(firstAccount)
                        }
                        TransactionType.EXPENSE -> {
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

    fun deleteRecordWithId(recordId: Long){
        viewModelScope.launch {
            useCaseExecutor.execute(
                deleteRecordWithIdUseCase,
                recordId
            ){
                //
            }
        }
    }

    fun addNewRecord(record: Record,firstAccount: Account, secondAccount: Account ? = null){
        addNewRecordAction(record, firstAccount, secondAccount)
    }

    fun showDetailsAction() {
        _showDetails.value = true
    }

    fun hideDetailsAction() {
        _showDetails.value = false
    }

    fun showDeleteAction() {
        _showDelete.value = true
    }

    fun hideDeleteAction() {
        _showDelete.value = false
    }




}