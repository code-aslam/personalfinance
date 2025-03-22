package com.hotdogcode.spendwise.presentation.analysis

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hotdogcode.spendwise.common.RANDOM_COLOR_LIST
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordForCategoryUseCase
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordsUseCase
import com.hotdogcode.spendwise.presentation.analysis.components.Bar
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

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor,
    private val getRecordsUseCase: GetRecordsUseCase,

    ) : BaseViewModel(useCaseExecutor){


    private val _dateSortedRecords = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())
    private val  map = mutableMapOf<Long,List<RecordWithCategoryAndAccount>>()


    private val _dateSortedRecordsPerMonth = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())
    val dateSortedRecordsPerMonth = _dateSortedRecordsPerMonth.asStateFlow()

    private val _barList = MutableStateFlow(listOf<Bar>())
    var barList = _barList.asStateFlow()


    private val _showDetails = MutableStateFlow(false)
    var showDetails = _showDetails.asStateFlow()

    private val _currentDate = MutableStateFlow(Calendar.getInstance())
    var currentDate = _currentDate.asStateFlow()

    init {
        getRecordsForCategory()
    }

    @SuppressLint("DefaultLocale")
    private fun getRecordsForCategory(){
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


    @RequiresApi(Build.VERSION_CODES.O)
    private fun groupDataByDaySorted(dataList: List<RecordWithCategoryAndAccount>): Map<LocalDate, List<RecordWithCategoryAndAccount>> {
        return dataList.groupBy {
            val instant = Instant.ofEpochMilli(it.date)
            instant.atZone(ZoneId.systemDefault()).toLocalDate()
        }.toSortedMap { date1, date2 -> date2.compareTo(date1) }
    }
    fun showDetailsAction() {
        _showDetails.value = true
    }

    fun hideDetailsAction() {
        _showDetails.value = false
    }

    fun updateCurrentMonth(months : Int){
        val newDate = _currentDate.value.clone() as Calendar // Create a new instance
        newDate.add(Calendar.MONTH, months) // Modify the new instance
        _currentDate.value = newDate // Update the state with the new instance
        updateData()
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

    private fun updateData(){
        _dateSortedRecordsPerMonth.value = filterRecordsByMonth(_dateSortedRecords.value, _currentDate.value.time).toMutableMap()
        map.clear()
        for((key, value) in _dateSortedRecordsPerMonth.value) {
            for (record in value) {
                if (record.transactionType == TransactionType.EXPENSE) {
                    if (!map.containsKey(record.categoryId)) {
                        map[record.categoryId] = listOf(record)
                    } else {
                        map[record.categoryId] = map[record.categoryId]!!.plus(record)
                    }
                }
            }
        }
        val list = mutableListOf<Bar>()
        var totalSpending = 0.0
        for((id,categories) in map){
            totalSpending += categories.sumOf { it.amount }
        }
        for((id,categories) in map){
            val bar = Bar(categoryId = id)
            bar.color = categories[0].categoryIcon.color
            bar.categoryIcon = categories[0].categoryIcon
            bar.categoryName = categories[0].categoryTitle
            bar.spending = categories.sumOf { it.amount }
            bar.percentage =   String.format("%.2f", ((bar.spending / totalSpending) * 100)).toDouble()
            list.add(bar)
        }
        _barList.value = list
    }
}